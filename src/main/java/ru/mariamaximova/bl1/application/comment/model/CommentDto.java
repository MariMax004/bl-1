package ru.mariamaximova.bl1.application.comment.model;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    private Long rating;

    private String comment;
}
