package ru.mariamaximova.bl1.application.rating.model;

import lombok.Data;

@Data
public class RatingDto {
    private Long id;
    private boolean is_active;
    private Long rating;

}
