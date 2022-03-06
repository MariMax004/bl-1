package ru.mariamaximova.bl1.application.rating.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.customer.domain.CustomerRepository;
import ru.mariamaximova.bl1.application.customer.model.CustomerDto;
import ru.mariamaximova.bl1.application.fiml.domain.FilmRepository;
import ru.mariamaximova.bl1.application.rating.domain.Rating;
import ru.mariamaximova.bl1.application.rating.domain.RatingRepository;
import ru.mariamaximova.bl1.application.rating.model.RatingDto;
import ru.mariamaximova.bl1.application.rating.model.ResponseRatingDto;
import ru.mariamaximova.bl1.application.rating.service.RatingService;
import ru.mariamaximova.bl1.error.ErrorDescription;

import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final FilmRepository filmRepository;

    private final CustomerRepository customerRepository;

    private final UserTransaction userTransaction;

    @Override
    public List<ResponseRatingDto> getRatings(Long filmId) {
        log.info("start getRatings({})", filmId);
        return ratingRepository.findAllByFilmId(filmRepository.getById(filmId)).stream()
                .map(this::convertToRatingDto).sorted(Comparator.comparingLong(ResponseRatingDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveRating(Long filmId, Long customerId, RatingDto commentDto) {
        log.info("start saveRating({}, {}, {})", filmId, customerId, commentDto);
        try {
            userTransaction.begin();
            Rating comment = ratingRepository.getByFilmIdAndCustomerId(filmRepository.getById(filmId),
                    customerRepository.getById(customerId));
            if (ObjectUtils.isEmpty(comment) || !ObjectUtils.isEmpty(commentDto.getId()) &&
                    comment.getId().equals(commentDto.getId())) {
                ratingRepository.save(convertToRating(filmId, customerId, commentDto));
                userTransaction.commit();
            } else {
                userTransaction.rollback();
                log.info("Error save uniq");
                throw ErrorDescription.SAVE_RATING_ERROR_UNIQ.exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("complete save");
    }


    @Override
    public void deleteRating(Long comment_id) {
        if(!ObjectUtils.isEmpty(ratingRepository.findById(comment_id))) {
            ratingRepository.deleteById(comment_id);
        } else {
            throw ErrorDescription.RATING_NOT_FOUND.exception();
        }
    }

    @Override
    public void updateStatusRating(Long comment_id) {
        ratingRepository.getById(comment_id).set_active(true);
    }

    private ResponseRatingDto convertToRatingDto(Rating comment) {
        ResponseRatingDto responseCommentDto = new ResponseRatingDto();
        responseCommentDto.setId(comment.getId());
        responseCommentDto.setCustomerDto(convertToCustomerDto(comment.getCustomerId()));
        responseCommentDto.setRating(comment.getRating());
        return responseCommentDto;
    }

    private CustomerDto convertToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }

    private Rating convertToRating(Long filmId, Long customerId, RatingDto commentDto) {
        Rating rating = new Rating();
        if (!ObjectUtils.isEmpty(commentDto.getId())) {
            rating.setId(commentDto.getId());
        }
        rating.setCustomerId(customerRepository.getById(customerId));
        rating.setFilmId(filmRepository.getById(filmId));
        rating.setRating(commentDto.getRating());
        return rating;
    }

}
