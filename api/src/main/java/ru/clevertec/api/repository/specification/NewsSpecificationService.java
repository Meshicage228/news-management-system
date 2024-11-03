package ru.clevertec.api.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.api.entity.NewsEntity.Fields.text;
import static ru.clevertec.api.entity.NewsEntity.Fields.title;

/**
 * Утилитарный класс для создания спецификаций запросов к сущностям новостей.
 *
 * <p>
 * Этот класс предоставляет статический метод для создания {@link Specification} на основе
 * заданного фильтра новостей.
 * </p>
 */
@UtilityClass
public class NewsSpecificationService {
    /**
     * Создает спецификацию для фильтрации новостей на основе заданного фильтра.
     *
     * @param filter Фильтр новостей, содержащий параметры для фильтрации.
     * @return Спецификация для фильтрации новостей.
     */
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
