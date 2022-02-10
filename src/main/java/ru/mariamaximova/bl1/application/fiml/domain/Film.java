package ru.mariamaximova.bl1.application.fiml.domain;

import lombok.*;
import ru.mariamaximova.bl1.application.comment.domain.Comment;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "film")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="film_seq")
    @SequenceGenerator(name="film_seq", sequenceName="film_seq", allocationSize=1)
    private Long id;

    @Column(name = "date_release")
    private Date dateRelease;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "filmId", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
