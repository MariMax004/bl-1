package ru.mariamaximova.bl1.application.comment.model;

import lombok.Data;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;

@Data
public class CommentDto {
    private Long id;

    private String comment;

    private RatingDto rating;

    private boolean is_active;

    public CommentDto() {
    }

    public CommentDto(Long id, String comment, RatingDto rating, boolean is_active) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.is_active = is_active;
    }
}
