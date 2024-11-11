package ru.clevertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.api.entity.CommentEntity;

/**
 * Репозиторий для работы с сущностями комментариев.
 *
 * <p>
 * Этот интерфейс предоставляет методы для выполнения CRUD операций
 * над сущностями {@link CommentEntity}, а также поддерживает спецификации и постраничную навигацию.
 * </p>
 *
 * @see CommentEntity
 */
@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long>,
                                            JpaSpecificationExecutor<CommentEntity>,
                                            PagingAndSortingRepository<CommentEntity, Long> {

}
