package ru.clevertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.api.entity.CommentEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long>,
                                            JpaSpecificationExecutor<CommentEntity>,
                                            PagingAndSortingRepository<CommentEntity, Long> {

}
