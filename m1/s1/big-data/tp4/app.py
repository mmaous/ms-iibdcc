from pyspark.sql import SparkSession
from pyspark.sql.functions import avg, col, count, max, min, when
from pyspark.sql.types import (
    DoubleType,
    IntegerType,
    StringType,
    StructField,
    StructType,
    TimestampType,
)

# 1. Initialize Spark Session connected to Docker Spark Master
spark = SparkSession.builder.appName(
    "TP PySpark Structured Streaming - Capteurs HDFS "
).getOrCreate()

spark.sparkContext.setLogLevel("WARN")

# 2. Define explicit schema
schema_sensors = StructType(
    [
        StructField("id", IntegerType(), True),
        StructField("timestamp", TimestampType(), True),
        StructField("capteur", StringType(), True),
        StructField("valeur", DoubleType(), True),
        StructField("unite", StringType(), True),
    ]
)

# 3. HDFS Directory Paths
source_path = "hdfs://namenode:8020/streaming/capteurs"

# Spark will auto-create these unique checkpoint paths to prevent conflicts
ckpt_stats_console = "hdfs://namenode:8020/streaming/checkpoints/stats_console"
ckpt_stats_parquet = "hdfs://namenode:8020/streaming/checkpoints/stats_parquet"
ckpt_alerts_console = "hdfs://namenode:8020/streaming/checkpoints/alerts_console"
ckpt_alerts_parquet = "hdfs://namenode:8020/streaming/checkpoints/alerts_parquet"

output_stats_parquet = "hdfs://namenode:8020/streaming/output/statistiques"
output_alertes_parquet = "hdfs://namenode:8020/streaming/output/alertes"

# 4. Read Streaming Source
df_stream = (
    spark.readStream.option("header", "true")
    .option("maxFilesPerTrigger", 1)
    .schema(schema_sensors)
    .csv(source_path)
)

# --- SECTION 17: COMPLEMENTARY WORK ---

# Dynamic anomaly rules: Temperature > 35.0 OR Humidity > 80.0
anomaly_condition = ((col("capteur").contains("TEMP")) & (col("valeur") > 35.0)) | (
    (col("capteur").contains("HUM")) & (col("valeur") > 80.0)
)

# Append status column 'statut'
df_with_status = df_stream.withColumn(
    "statut", when(anomaly_condition, "ANORMAL").otherwise("NORMAL")
)

alerts = df_with_status.filter(col("statut") == "ANORMAL")

stats_sensors = df_stream.groupBy("capteur").agg(
    avg("valeur").alias("moyenne_valeur"),
    min("valeur").alias("valeur_min"),
    max("valeur").alias("valeur_max"),
    count("*").alias("nombre_mesures"),
)

filtered_stats = stats_sensors.filter(col("moyenne_valeur") > 25.0)

# --- OUTPUT WRITE QUERY CONFIGURATIONS (SINKS) ---

# Sink 1: Stats to Console (Complete)
query_stats_console = (
    filtered_stats.writeStream.outputMode("complete")
    .format("console")
    .option("truncate", "false")
    .option("checkpointLocation", ckpt_stats_console)
    .trigger(processingTime="10 seconds")
    .start()
)


# Sink 2: FIXED - Stats to Parquet using foreachBatch workaround
def write_stats_to_parquet(batch_df, batch_id):
    # This safely overwrites the Parquet file with the updated stats every 10 seconds
    batch_df.write.mode("overwrite").parquet(output_stats_parquet)


query_stats_parquet = (
    stats_sensors.writeStream.outputMode("complete")
    .foreachBatch(write_stats_to_parquet)
    .option("checkpointLocation", ckpt_stats_parquet)
    .trigger(processingTime="10 seconds")
    .start()
)

# Sink 3: Alerts to Console (Append)
query_alerts_console = (
    alerts.writeStream.outputMode("append")
    .format("console")
    .option("truncate", "false")
    .option("checkpointLocation", ckpt_alerts_console)
    .trigger(processingTime="10 seconds")
    .start()
)

# Sink 4: Alerts to Parquet (Append) -> Works natively because alerts don't use aggregations!
query_alerts_parquet = (
    alerts.writeStream.outputMode("append")
    .format("parquet")
    .option("path", output_alertes_parquet)
    .option("checkpointLocation", ckpt_alerts_parquet)
    .trigger(processingTime="10 seconds")
    .start()
)

# Await any stream termination
spark.streams.awaitAnyTermination()
