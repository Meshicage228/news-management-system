package ru.clevertec.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@FieldNameConstants
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(value = TemporalType.DATE)
    private LocalDate time;

    private String text;

    @Column(name = "author_name")
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private NewsEntity newsEntity;
}
