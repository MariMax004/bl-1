package ru.mariamaximova.bl1.application.comment.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.mariamaximova.bl1.application.comment.domain.Comment;
import ru.mariamaximova.bl1.application.comment.domain.CommentRepository;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.comment.service.CommentService;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.customer.domain.CustomerRepository;
import ru.mariamaximova.bl1.application.customer.model.CustomerDto;
import ru.mariamaximova.bl1.application.fiml.domain.FilmRepository;
import ru.mariamaximova.bl1.error.ErrorDescription;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final FilmRepository filmRepository;

    private final CustomerRepository customerRepository;

    @Override
    public List<ResponseCommentDto> getComments(Long filmId) {
        log.info("start getComment({})", filmId);
        return commentRepository.findAllByFilmId(filmRepository.getById(filmId)).stream()
                .map(this::convertToCommentDto).sorted(Comparator.comparingLong(ResponseCommentDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void saveComment(Long filmId, Long customerId, CommentDto commentDto) {
        log.info("start saveComment({}, {}, {})", filmId, customerId, commentDto);
        Comment comment = commentRepository.getByFilmIdAndCustomerId(filmRepository.getById(filmId),
                customerRepository.getById(customerId));
        if (ObjectUtils.isEmpty(comment) || !ObjectUtils.isEmpty(commentDto.getId()) &&
                comment.getId().equals(commentDto.getId())) {
            commentRepository.save(convertToComment(filmId, customerId, commentDto));
        } else {
            log.info("Error save uniq");
            throw ErrorDescription.SAVE_COMMENT_ERROR_UNIQ.exception();
        }
        log.info("complete save");
    }

    private ResponseCommentDto convertToCommentDto(Comment comment) {
        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        responseCommentDto.setId(comment.getId());
        responseCommentDto.setCustomerDto(convertToCustomerDto(comment.getCustomerId()));
        responseCommentDto.setComment(comment.getComment());
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

    private Comment convertToComment(Long filmId, Long customerId, CommentDto commentDto) {
        Comment comment = new Comment();
        if (!ObjectUtils.isEmpty(commentDto.getId())) {
            comment.setId(commentDto.getId());
        }
        comment.setCustomerId(customerRepository.getById(customerId));
        comment.setFilmId(filmRepository.getById(filmId));
        comment.setComment(commentDto.getComment());
        comment.setRating(commentDto.getRating());
        return comment;
    }

}
