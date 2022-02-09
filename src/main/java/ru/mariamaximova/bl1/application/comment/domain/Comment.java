package ru.mariamaximova.bl1.application.comment.domain;

import lombok.*;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.fiml.domain.Film;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comment_seq")
    @SequenceGenerator(name="comment_seq", sequenceName="comment_seq", allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film filmId;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "comment")
    private String comment;
}
