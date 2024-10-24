package ru.clevertec.newsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Getter
@Setter

@Entity
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(value = TemporalType.DATE)
    private LocalDate time;

    @Column(name = "news_title")
    private String title;

    private String text;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "newsEntity")
    private List<CommentEntity> comments = new ArrayList<>();
}
