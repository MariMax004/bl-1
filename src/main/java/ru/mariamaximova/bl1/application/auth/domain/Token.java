package ru.mariamaximova.bl1.application.auth.domain;

import lombok.*;
import ru.mariamaximova.bl1.application.customer.domain.Customer;

import javax.persistence.*;

@Data
@Entity
@Table(name = "token")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="token_seq")
    @SequenceGenerator(name="token_seq", sequenceName="token_seq", allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @Column(name = "token")
    private String token;
}
