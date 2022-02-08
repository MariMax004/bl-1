package ru.mariamaximova.bl1.application.comment.domain;

import lombok.*;
import ru.mariamaximova.bl1.application.customer.domain.Customer;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Customer customerId;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "comment")
    private String comment;
}
