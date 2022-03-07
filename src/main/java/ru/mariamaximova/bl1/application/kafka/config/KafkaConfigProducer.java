package ru.mariamaximova.bl1.application.kafka.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.kafka.serialize.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class KafkaConfigProducer {

        @Bean
        public KafkaProducer<String, ResponseCommentDto> producerFactory() {
            Map<String, Object> config = new HashMap<>();

            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

            return new KafkaProducer<>(config);
        }


}
