package ru.clevertec.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
/**
 * Сущность, представляющая комментарий.
 *
 * <p>
 * Данный класс отображает таблицу "comments" в базе данных и содержит информацию о
 * комментарии, включая дату создания, текст комментария, имя автора и связанную новость.
 * </p>
 */

@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(exclude = "newsEntity")
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "comments")
public class CommentEntity {

    /**
     * Уникальный идентификатор комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата создания комментария.
     *
     * <p>
     * Поле автоматически заполняется при создании записи и хранит дату в формате ISO.
     * </p>
     */
    @Column(name = "creation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreationTimestamp
    @Temporal(value = TemporalType.DATE)
    private LocalDate time;

    /**
     * Текст комментария.
     */
    private String text;

    /**
     * Имя автора комментария.
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     * Связанная новость, к которой относится комментарий.
     *
     * <p>
     * Устанавливает связь с сущностью {@link NewsEntity}.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    @JsonBackReference
    private NewsEntity newsEntity;
}
