package ma.enset.tp5kafka.ex1;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class TextCleanerApp {

  public static void main(String[] args) {
    // 1
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "text-cleaner-app");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    StreamsBuilder builder = new StreamsBuilder();

    // 2
    KStream<String, String> sourceStream = builder.stream("text-input");

    // 3
    KStream<String, String> cleanedStream = sourceStream.mapValues(text -> {
      if (text == null) return "";
      return text.trim().replaceAll("\\s+", " ").toUpperCase();
    });

    // 4
    KStream<String, String> validStream = cleanedStream.filter((key, value) -> isValid(value));
    KStream<String, String> invalidStream = cleanedStream.filter((key, value) -> !isValid(value));

    // 5
    validStream.to("text-clean");
   // invalidStream.to("text-dead-letter");
    invalidStream.mapValues(value -> "Message rejeté").to("text-dead-letter");

    // 6 shoudl add try-catch
    KafkaStreams streams = new KafkaStreams(builder.build(), props);
    final CountDownLatch latch = new CountDownLatch(1);

    Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
      @Override
      public void run() {
        streams.close();
        latch.countDown();
      }
    });

    try {
      streams.start();
      System.out.println("TextCleanerApp is running and waiting for messages...");
      latch.await(); // this keeps the main thread alive!
    } catch (Throwable e) {
      System.exit(1);
    }
    System.exit(0);
  }

  private static boolean isValid(String text) {
    if (text.isEmpty()) return false;

    if (text.length() > 100) return false;

    return !text.contains("HACK") && !text.contains("SPAM") && !text.contains("XXX");
  }
}