package ru.clevertec.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.core.entity.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long>,
                                        JpaSpecificationExecutor<NewsEntity>,
                                        PagingAndSortingRepository<NewsEntity, Long> {
}
