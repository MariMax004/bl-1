package ru.mariamaximova.bl1.application.rating.model;

import lombok.Data;
import ru.mariamaximova.bl1.application.customer.model.CustomerDto;

@Data
public class ResponseRatingDto extends RatingDto {
    private CustomerDto customerDto;
}
