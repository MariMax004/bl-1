package ru.mariamaximova.bl1.application.kafka.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.kafka.serialize.DeserializeJson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfigConsumer {
    @Bean
    public ConsumerFactory<String, ResponseCommentDto> commentDtoConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DeserializeJson.class.getName());
        return new DefaultKafkaConsumerFactory<>(config );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResponseCommentDto> commentDtoKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResponseCommentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(commentDtoConsumerFactory());
        return factory;
    }


}
