package ru.clevertec.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.core.entity.CommentEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {
}
