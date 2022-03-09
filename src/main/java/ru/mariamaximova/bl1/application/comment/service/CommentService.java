package ru.mariamaximova.bl1.application.comment.service;

import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;

import java.util.List;

public interface CommentService {
    List<ResponseCommentDto> getComments(Long filmId);
    ResponseCommentDto getComment(long id);

    List<ResponseCommentDto> getCommentsToModerator();

    void saveComment(Long filmId, Long customerId, CommentDto commentDto);
    void deleteComment(Long id);
    void updateStatusComment(Long id, Boolean flag);
}
