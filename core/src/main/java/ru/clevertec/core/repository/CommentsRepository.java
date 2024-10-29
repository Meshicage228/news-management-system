package ru.clevertec.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.core.entity.CommentEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long>,
                                            JpaSpecificationExecutor<CommentEntity>,
                                            PagingAndSortingRepository<CommentEntity, Long> {

}
