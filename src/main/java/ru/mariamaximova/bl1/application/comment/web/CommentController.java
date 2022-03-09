package ru.mariamaximova.bl1.application.comment.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.mariamaximova.bl1.application.comment.domain.CommentRepository;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.comment.service.CommentService;
import ru.mariamaximova.bl1.application.rating.domain.RatingRepository;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;
import ru.mariamaximova.bl1.application.rating.service.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;


    @GetMapping(value = "/film/{filmId}/comments")
    public List<ResponseCommentDto> getComments(@PathVariable Long filmId) {
        return commentService.getComments(filmId);
    }

    @GetMapping(value = "/updateStatus/{commentId}/{flag}")
    public void updateStatusComment(@PathVariable Long commentId,
                                    @PathVariable Boolean flag){

        commentService.updateStatusComment(commentId, flag);
    }

    @DeleteMapping(value = "/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }


    @PostMapping(value = "/film/{filmId}/customer/{customerId}/comment/save")
    public void saveComment(@PathVariable Long filmId,
                            @PathVariable Long customerId,
                            @RequestBody CommentDto commentDto) {
        commentService.saveComment(filmId, customerId, commentDto);
    }
}
