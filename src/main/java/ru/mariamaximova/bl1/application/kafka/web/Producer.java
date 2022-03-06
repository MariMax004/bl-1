package ru.mariamaximova.bl1.application.kafka.web;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;


@RestController
@RequestMapping("kafka")
public class Producer {

        @Autowired

        private KafkaProducer<String, CommentDto> kafkaTemplate;
        private ProducerRecord<String,CommentDto> record;

        private static final String TOPIC = "Kafka_Example_json";

        @GetMapping("/publish/{name}")
        public String post(@PathVariable("name") final String name) {
            record= new ProducerRecord(TOPIC, new CommentDto(1L,name, new RatingDto(1L,true, 3L), true));
            kafkaTemplate.send(record);

            return "Published successfully";
        }
}
