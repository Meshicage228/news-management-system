package ru.clevertec.api.service.news;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.repository.NewsRepository;
import ru.clevertec.api.service.impl.NewsServiceImpl;
import ru.clevertec.api.util.annotation.PersistencePostgreSQLTests;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@PersistencePostgreSQLTests
@DisplayName("News integrated tests")
class NewsServiceImplTest {

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private NewsRepository newsRepository;

    private Long newsId;
    private CreateNewsDto createNewsDto;

    @BeforeEach
    public void setUp() {
        newsId = 1L;
        createNewsDto = CreateNewsDto.builder()
                .title("Test News Title")
                .text("Test News Content")
                .authorName("Vlad")
                .build();
    }

    @Test
    @DisplayName("Create news successfully : returns created")
    public void testCreateNews() {
        // When
        CreatedNewsDto createdNewsDto = newsService.createNews(createNewsDto);
        Optional<NewsEntity> newsFromDb = newsRepository.findById(createdNewsDto.getId());

        // Then
        assertTrue(newsFromDb.isPresent());
        assertNotNull(newsFromDb.get().getId());
        assertEquals(createNewsDto.getTitle(), newsFromDb.get().getTitle());
        assertEquals(createNewsDto.getText(), newsFromDb.get().getText());
        assertEquals(createNewsDto.getAuthorName(), newsFromDb.get().getAuthorName());
    }

    @Test
    @Transactional
    @DisplayName("Full news update : returns updated")
    public void testFullNewsUpdate() {
        // Given
        UpdateNewsDto updateNewsDto = UpdateNewsDto.builder()
                .title("Updated Title")
                .text("Updated Content")
                .authorName("Updated Author")
                .build();

        // When
        UpdatedNewsDto updatedNewsDto = newsService.fullNewsUpdate(newsId, updateNewsDto);

        // Then
        assertEquals(newsId, updatedNewsDto.getId());
        assertEquals(updateNewsDto.getTitle(), updatedNewsDto.getTitle());
        assertEquals(updateNewsDto.getAuthorName(), updatedNewsDto.getAuthorName());
        assertEquals(updateNewsDto.getText(), updatedNewsDto.getText());
    }

    @Test
    @DisplayName("Get shorted news")
    public void testGetAllShortNews() {
        // When
        Page<ShortNewsDto> newsPage = newsService.getAllShortNews(0, 10, null);

        // Then
        assertNotNull(newsPage);
        assertFalse(newsPage.isEmpty());
        assertEquals(1, newsPage.getTotalElements());
    }
}