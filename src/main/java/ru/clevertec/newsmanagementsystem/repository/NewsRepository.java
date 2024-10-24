package ru.clevertec.newsmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsmanagementsystem.entity.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
