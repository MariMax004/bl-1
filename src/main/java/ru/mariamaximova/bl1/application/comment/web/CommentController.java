package ru.mariamaximova.bl1.application.comment.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.comment.service.CommentService;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;
import ru.mariamaximova.bl1.application.rating.service.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/film/{filmId}/comments")
    public List<ResponseCommentDto> getComments(@PathVariable Long filmId) {
        return commentService.getComments(filmId);
    }

    @PostMapping(value = "/film/{filmId}/customer/{customerId}/comment/save")
    public void saveComment(@PathVariable Long filmId,
                            @PathVariable Long customerId,
                            @RequestBody CommentDto commentDto) {
        commentService.saveComment(filmId, customerId, commentDto);
    }
}
