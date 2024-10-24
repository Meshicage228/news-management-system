package ru.clevertec.newsmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsmanagementsystem.entity.CommentEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {
}
