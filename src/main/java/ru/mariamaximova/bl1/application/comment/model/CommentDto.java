package ru.mariamaximova.bl1.application.comment.model;

import lombok.Data;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;

@Data
public class CommentDto {
    private Long id;

    private String comment;

    private RatingDto rating;
}
