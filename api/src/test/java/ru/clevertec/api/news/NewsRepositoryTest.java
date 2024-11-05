package ru.clevertec.api.news;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.api.ApiApplication;
import ru.clevertec.api.config.PostgresContainerConfig;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.repository.NewsRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PostgresContainerConfig.class)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:ru/clevertec/api/db/insert-data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS, scripts = "classpath:ru/clevertec/api/db/clean-up.sql")
public class NewsRepositoryTest {
    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void testCreateNews() {
        System.out.println();
    }

    @Test
    public void testFindByTitle() {
        List<NewsEntity> all = newsRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
    }
}
