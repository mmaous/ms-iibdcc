package ma.enset;


import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;


public class SparkSQLApp {

    public static void main(String[] args) throws AnalysisException {
        // Initialize SparkSession
        SparkSession ss = SparkSession.builder()
            .appName("SparkSQLApp")
            .master("local[*]")
            .config("spark.driver.bindAddress", "127.0.0.1")
            .config("spark.driver.host", "127.0.0.1")
            .getOrCreate();
        ss.sparkContext().setLogLevel("ERROR");
        // -
        // 1. Data Loading & Exploration
        // -
        System.out.println("--- 1. Data Loading & Exploration ---");

        // 1.1 Load the CSV file into a Spark DataFrame
        Dataset<Row> df1 = ss
            .read()
            .option("header", true)
            .option("inferSchema", true)
            .csv("rentals.csv");

        // Dropping nulls (good practice from your professor's template)
        Dataset<Row> df_na = df1.na().drop();

        // 1.2 Display the schema
        df_na.printSchema();

        // 1.3 Show the first 5 rows
        df_na.show(5);

        // 1.4 How many rentals are in the dataset?
        System.out.println("Total rentals in dataset: " + df_na.count());

        // -
        // 2. Create a Temporary View
        // -
        // Create the SQL view: bike_rentals_view
        df_na.createTempView("bike_rentals_view");

        // -
        // 3. Basic SQL Queries
        // -
        System.out.println("\n--- 3. Basic SQL Queries ---");

        // 3.1 List all rentals longer than 30 minutes
        System.out.println("Rentals > 30 minutes:");
        ss.sql(
            "SELECT * FROM bike_rentals_view WHERE duration_minutes > 30"
        ).show();

        // 3.2 Show all rentals starting at "Station A"
        System.out.println("Rentals starting at Station A:");
        ss.sql(
            "SELECT * FROM bike_rentals_view WHERE start_station = 'Station A'"
        ).show();

        // 3.3 Calculate the total revenue (sum of the column price)
        System.out.println("Total Revenue:");
        ss.sql(
            "SELECT SUM(price) as total_revenue FROM bike_rentals_view"
        ).show();

        // -
        // 4. Aggregation Queries
        // -
        System.out.println("\n--- 4. Aggregation Queries ---");

        // 4.1 Count how many rentals were made from each start station
        System.out.println("Rentals per start station:");
        ss.sql(
            "SELECT start_station, COUNT(*) as total_rentals FROM bike_rentals_view GROUP BY start_station"
        ).show();

        // 4.2 Compute the average rental duration per start station
        System.out.println("Average rental duration per start station:");
        ss.sql(
            "SELECT start_station, AVG(duration_minutes) as avg_duration FROM bike_rentals_view GROUP BY start_station"
        ).show();

        // 4.3 Identify the station with the highest number of rentals
        System.out.println("Station with the highest number of rentals:");
        ss.sql(
            "SELECT start_station, COUNT(*) as rental_count FROM bike_rentals_view GROUP BY start_station ORDER BY rental_count DESC LIMIT 1"
        ).show();

        // -
        // 5. Time-Based Analysis
        // -
        System.out.println("\n--- 5. Time-Based Analysis ---");

        // 5.1 Extract the hour from start time
        System.out.println("Hour extracted from start_time:");
        ss.sql(
            "SELECT start_time, HOUR(start_time) as start_hour FROM bike_rentals_view"
        ).show(5);

        // 5.2 Count how many bikes were rented per hour (identify peak hours)
        System.out.println("Bikes rented per hour (Peak Hours):");
        ss.sql(
            "SELECT HOUR(start_time) as hour_of_day, COUNT(*) as rentals_count FROM bike_rentals_view GROUP BY hour_of_day ORDER BY rentals_count DESC"
        ).show();

        // 5.3 Determine the most popular start station during the morning (7-12)
        System.out.println(
            "Most popular start station in the morning (7 AM - 12 PM):"
        );
        ss.sql(
            "SELECT start_station, COUNT(*) as morning_rentals FROM bike_rentals_view WHERE HOUR(start_time) BETWEEN 7 AND 12 GROUP BY start_station ORDER BY morning_rentals DESC LIMIT 1"
        ).show();

        // -
        // 6. User Behavior Analysis
        // -
        System.out.println("\n--- 6. User Behavior Analysis ---");

        // 6.1 Compute the average age of users
        System.out.println("Average age of users:");
        ss.sql("SELECT AVG(age) as average_age FROM bike_rentals_view").show();

        // 6.2 Count users by gender
        System.out.println("User count by gender:");
        ss.sql(
            "SELECT gender, COUNT(*) as user_count FROM bike_rentals_view GROUP BY gender"
        ).show();

        // 6.3 Find which age group rents bicycles the most (18-30, 31-40, 41-50, 51+)
        System.out.println("Age group that rents bicycles the most:");
        ss.sql(
            "SELECT " +
                "  CASE " +
                "    WHEN age BETWEEN 18 AND 30 THEN '18-30' " +
                "    WHEN age BETWEEN 31 AND 40 THEN '31-40' " +
                "    WHEN age BETWEEN 41 AND 50 THEN '41-50' " +
                "    WHEN age >= 51 THEN '51+' " +
                "    ELSE 'Other' " +
                "  END AS age_group, " +
                "  COUNT(*) as total_rentals " +
                "FROM bike_rentals_view " +
                "GROUP BY age_group " +
                "ORDER BY total_rentals DESC " +
                "LIMIT 1"
        ).show();

        // Stop the SparkSession
        ss.stop();
    }
}
