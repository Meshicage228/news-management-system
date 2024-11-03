package ru.clevertec.api.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.api.entity.CommentEntity.Fields.authorName;
import static ru.clevertec.api.entity.CommentEntity.Fields.newsEntity;

/**
 * Утилитарный класс для создания спецификаций запросов к сущностям комментариев.
 *
 * <p>
 * Этот класс предоставляет статический метод для создания {@link Specification} на основе
 * заданного фильтра комментариев.
 * </p>
 */
@UtilityClass
public class CommentSpecificationService {
    /**
     * Создает спецификацию для фильтрации комментариев на основе заданного фильтра.
     *
     * @param filter Фильтр комментариев, содержащий параметры для фильтрации.
     * @return Спецификация для фильтрации комментариев.
     */
    public static Specification<CommentEntity> createCommentSpecification(CommentFilter filter){
        return (root, query, builder) -> {
            if(filter == null){
                return builder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            String commentContent = filter.getText();
            Long newsId = filter.getNewsId();
            String commentAuthor = filter.getAuthorName();

            Optional.ofNullable(commentContent)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.isBlank())
                    .ifPresent(s -> {
                        predicates.add(builder.like(root.get(CommentEntity.Fields.text), String.format("%%%s%%", s)));
                    });

            Optional.ofNullable(commentAuthor)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.isBlank())
                    .ifPresent(s -> {
                        predicates.add(builder.like(root.get(authorName), String.format("%%%s%%", s)));
                    });

            Optional.ofNullable(newsId)
                    .ifPresent(s -> {
                        predicates.add(builder.equal(root.get(newsEntity).get(NewsEntity.Fields.id), newsId));
                    });

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
