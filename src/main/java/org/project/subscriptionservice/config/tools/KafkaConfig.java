package org.project.subscriptionservice.config.tools;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableKafka
public class KafkaConfig {

    @Value("${spring.data.kafka_servers}")
    private final List<String> SERVERS;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configMap = new HashMap<>();
//        log.info("=== (Producer)Boostrap Server : {} ===", String.join(",", SERVERS));

        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", SERVERS));
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        configMap.put(ProducerConfig.ACKS_CONFIG, "1");
        configMap.put(ProducerConfig.RETRIES_CONFIG, 2);
        configMap.put(ProducerConfig.LINGER_MS_CONFIG, 25);
        configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, "49152");
        configMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864);

        configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);

        configMap.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
        configMap.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        configMap.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 50000);

        return new DefaultKafkaProducerFactory<>(configMap);
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configMap = new HashMap<>();
//        log.info("=== (Consumer)Boostrap Server : {} ===", String.join(",", SERVERS));

        // I put as list string so kafka bootstrap know only comma with more server
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", SERVERS));
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "Subscribe");
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configMap.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        configMap.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class.getName());

        configMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 400);
        configMap.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 50000);
        configMap.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);

        configMap.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 1000);
        configMap.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 10000);
        configMap.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 600000);
        configMap.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);

        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

}
