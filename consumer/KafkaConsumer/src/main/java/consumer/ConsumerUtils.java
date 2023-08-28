package consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.UvIndex;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ConsumerUtils {

    private ConsumerUtils() {
        
    }
    
    public static void outputMessageDataToConsole(String consumerId, ConsumerRecord<String, String> entry) {
        System.out.printf("Message received by %s from topic: %s%n", consumerId, entry.topic());
        System.out.printf("Partition: %s, Offset: %s%n", entry.partition(), entry.offset());
        System.out.printf("key = %s, value = %s%n", entry.key(), entry.value());
    }
    
    public static UvIndex convertMessageToUvIndex(String key, String value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UvIndex index = mapper.readValue(value, UvIndex.class);
        index.setCity(key);
        return index;
    }

    public static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }

}
