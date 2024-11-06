package ru.clevertec.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая новость.
 *
 * <p>
 * Данный класс отображает таблицу "news" в базе данных и содержит информацию о
 * новости, включая дату создания, заголовок, текст и связанные комментарии.
 * </p>
 */

@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(exclude = "comments")
@Getter
@Setter

@Entity
@Table(name = "news")
public class NewsEntity implements Serializable {

    /**
     * Уникальный идентификатор новости.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата создания новости.
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
     * Заголовок новости.
     */
    @Column(name = "news_title")
    private String title;

    /**
     * Текст новости.
     */
    private String text;

    /**
     * Автор новости.
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     * Список комментариев, связанных с новостью.
     *
     * <p>
     * Комментарии загружаются лениво и могут быть удалены при
     * удалении новости.
     * </p>
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "newsEntity", orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();
}
