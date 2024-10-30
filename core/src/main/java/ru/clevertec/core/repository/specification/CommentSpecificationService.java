package ru.clevertec.core.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.core.dto.filter.CommentFilter;
import ru.clevertec.core.entity.CommentEntity;
import ru.clevertec.core.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.core.entity.CommentEntity.Fields.*;

@UtilityClass
public class CommentSpecificationService {
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
