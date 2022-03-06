package ru.mariamaximova.bl1.application.comment.model;

import lombok.Data;
import ru.mariamaximova.bl1.application.customer.model.CustomerDto;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;

@Data
public class ResponseCommentDto extends CommentDto{
    private CustomerDto customerDto;

}
