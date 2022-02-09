package ru.mariamaximova.bl1.application.comment.model;

import lombok.Data;
import ru.mariamaximova.bl1.application.customer.model.CustomerDto;

@Data
public class CommentDto {
    private Long id;

    private CustomerDto customerDto;

    private Long rating;

    private String comment;
}
