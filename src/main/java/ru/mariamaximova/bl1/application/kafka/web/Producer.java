package ru.mariamaximova.bl1.application.kafka.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class Producer {

        private final KafkaProducer<String, ResponseCommentDto> kafkaTemplate;

        private final CommentService commentService;

        private static final String TOPIC = "Kafka_Example_json";

        @Scheduled(cron = "0 * * * * *")
        @Transactional
        public void sendToCheck() {
            log.info("send message for admin");
            commentService.getCommentsToModerator().forEach(it -> kafkaTemplate.send(new ProducerRecord(TOPIC,it)));
//            ProducerRecord<String, ResponseCommentDto> record = new ProducerRecord(TOPIC,commentService.getComment(id));
//            kafkaTemplate.send(record);
            log.info("messages send");
        }
}
