package ru.clevertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.api.entity.NewsEntity;

/**
 * Репозиторий для работы с сущностями новостей.
 *
 * <p>
 * Этот интерфейс предоставляет методы для выполнения CRUD операций
 * над сущностями {@link NewsEntity}, а также поддерживает спецификации и постраничную навигацию.
 * </p>
 *
 * @see NewsEntity
 */
@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long>,
                                        JpaSpecificationExecutor<NewsEntity>,
                                        PagingAndSortingRepository<NewsEntity, Long> {
}
