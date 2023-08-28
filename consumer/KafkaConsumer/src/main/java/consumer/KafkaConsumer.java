package consumer;

import org.apache.kafka.clients.consumer.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaConsumer {

    public static void main(String[] args) {
        try {
            final Properties props = ConsumerUtils.loadConfig("src/main/resources/client.properties");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
            
            org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer1 = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
            org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer2 = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
            consumer1.subscribe(List.of("uv_index"));
            consumer2.subscribe(List.of("uv_index"));
            
            while (true) {
                ConsumerRecords<String, String> records1 = consumer1.poll(Duration.ofMillis(100));
                ConsumerRecords<String, String> records2 = consumer2.poll(Duration.ofMillis(100));
                outputData("Consumer1", records1);
                outputData("Consumer2", records2);
            }
            
        } catch (IOException e) {
            System.exit(0);
        }
    }

    private static void outputData(String consumerId, ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> entry : records) {
            ConsumerUtils.outputMessageDataToConsole(consumerId, entry);
            File excelFile = new File("src/main/resources/UvIndex.xlsx");
            ExcelUtils.outputUvDataToExcel(entry.key(), entry.value(), excelFile);
        }
    }

}
