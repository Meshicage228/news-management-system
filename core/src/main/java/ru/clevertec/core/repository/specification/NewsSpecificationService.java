package ru.clevertec.core.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.core.dto.filter.NewsFilter;
import ru.clevertec.core.entity.NewsEntity;

import static ru.clevertec.core.entity.NewsEntity.Fields.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class NewsSpecificationService {
    public static Specification<NewsEntity> createNewsSpecification(NewsFilter filter){
        return (root, query, builder) -> {
            if(filter == null){
                return builder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            String newsText = filter.getText();
            String newsTitle = filter.getTitle();

            Optional.ofNullable(newsText)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.isBlank())
                    .ifPresent(s -> {
                        predicates.add(builder.like(root.get(text), String.format("%%%s%%", s)));
                    });

            Optional.ofNullable(newsTitle)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.isBlank())
                    .ifPresent(s -> {
                        predicates.add(builder.like(root.get(title), String.format("%%%s%%", s)));
                    });

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
