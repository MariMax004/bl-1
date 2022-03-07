package ru.mariamaximova.bl1.application.kafka.web;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mariamaximova.bl1.application.comment.domain.CommentRepository;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.comment.service.CommentService;
import ru.mariamaximova.bl1.application.comment.service.impl.CommentServiceImpl;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;


@RestController
@RequestMapping("kafka")
@RequiredArgsConstructor
public class Producer {


        private final KafkaProducer<String, ResponseCommentDto> kafkaTemplate;
        private final CommentService commentService;

        private static final String TOPIC = "Kafka_Example_json";

        @GetMapping("/publish/{id}")
        public String post(@PathVariable("id") Long id) {
            ProducerRecord<String, ResponseCommentDto> record = new ProducerRecord(TOPIC,commentService.getComment(id));
            kafkaTemplate.send(record);

            return "Published successfully";
        }
}
