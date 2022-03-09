package ru.mariamaximova.bl1.application.rating.domain;

import lombok.*;
import ru.mariamaximova.bl1.application.customer.domain.Customer;
import ru.mariamaximova.bl1.application.fiml.domain.Film;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rating")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rating_seq")
    @SequenceGenerator(name="rating_seq", sequenceName="rating_seq", allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film filmId;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "is_moderated")
    private boolean isModerated;
}

