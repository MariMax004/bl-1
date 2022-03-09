package ru.mariamaximova.bl1.application.comment.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariamaximova.bl1.application.comment.domain.Comment;
import ru.mariamaximova.bl1.application.comment.domain.CommentRepository;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;
import ru.mariamaximova.bl1.application.comment.service.CommentService;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.customer.model.CustomerDto;
import ru.mariamaximova.bl1.application.rating.domain.RatingRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class DeleteCommentTask {

    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    @Scheduled(cron = "5 4 * * * *")
    @Transactional
    public void deleteNotActiveComments() {
        List<ResponseCommentDto> responseCommentDto = commentRepository.getCommentsForDelete().stream()
                .map(this::convertToCommentDto).sorted(Comparator.comparingLong(ResponseCommentDto::getId))
                .collect(Collectors.toList());
        responseCommentDto.forEach(it -> ratingRepository.deleteById(it.getId()));
        responseCommentDto.forEach(it -> commentRepository.deleteById(it.getId()));
        log.info(responseCommentDto.toString());

    }

    private ResponseCommentDto convertToCommentDto(Comment comment) {
        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        responseCommentDto.setId(comment.getId());
        responseCommentDto.setCustomerDto(convertToCustomerDto(comment.getCustomerId()));
        responseCommentDto.setComment(comment.getComment());
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
}
