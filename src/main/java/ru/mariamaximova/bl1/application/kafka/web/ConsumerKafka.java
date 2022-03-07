package ru.mariamaximova.bl1.application.kafka.web;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;

@Service
public class ConsumerKafka {

//        @KafkaListener(topics = "Kafka_Example", groupId = "group_id")
//        public void consume(String message) {
//            System.out.println("Consumed message: " + message);
//        }
//

        @KafkaListener(topics = "Kafka_Example_json", groupId = "group_json",
                containerFactory = "commentDtoKafkaListenerFactory")
        public void consumeJson(ResponseCommentDto responseCommentDto) {
            System.out.println("Consumed JSON Message: " + responseCommentDto);
        }

}
