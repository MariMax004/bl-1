package ru.mariamaximova.bl1.application.customer.domain;

import lombok.*;
import ru.mariamaximova.bl1.application.auth.domain.Token;
import ru.mariamaximova.bl1.application.comment.domain.Comment;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="customer_seq")
    @SequenceGenerator(name="customer_seq", sequenceName="customer_seq", allocationSize=1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_moderator")
    private boolean is_moderator;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    private List<Token> tokens;


}
