package ru.clevertec.api.service.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.UpdateNewsDto;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.NewsMapper;
import ru.clevertec.api.repository.NewsRepository;
import ru.clevertec.api.service.impl.cache.CacheNewsService;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cache service module tests")
class CacheNewsServiceModuleTest {
    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    @Spy
    private CacheNewsService cacheNewsService;

    private Long newsId;
    private NewsEntity newsEntity;
    private UpdateNewsDto updateNewsDto;

    @BeforeEach
    public void setUp() {
        newsId = 1L;
        newsEntity = new NewsEntity();
        newsEntity.setId(newsId);
        updateNewsDto = new UpdateNewsDto();
    }

    @Test
    @DisplayName("Get news by ID successfully")
    public void testGetNewsById() {
        // Given
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(newsEntity));

        // When
        NewsEntity result = cacheNewsService.getNewsById(newsId);

        // Then
        verify(newsRepository).findById(newsId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get news by ID not found")
    public void testGetNewsByIdNotFound() {
        // Given
        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NewsNotFoundException.class, () -> cacheNewsService.getNewsById(newsId));
    }

    @Test
    @DisplayName("Save news successfully")
    public void testSaveNews() {
        // Given
        when(newsRepository.save(any(NewsEntity.class))).thenReturn(newsEntity);

        // When
        NewsEntity result = cacheNewsService.saveNews(newsEntity);

        // Then
        verify(newsRepository).save(newsEntity);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Full update news successfully")
    public void testFullUpdateNews() {
        // Given
        when(newsMapper.fullUpdate(any(NewsEntity.class), any(UpdateNewsDto.class))).thenReturn(newsEntity);

        // When
        NewsEntity result = cacheNewsService.fullUpdateNews(newsEntity, updateNewsDto);

        // Then
        verify(newsMapper).fullUpdate(newsEntity, updateNewsDto);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Patch update news successfully")
    public void testPatchUpdateNews() {
        // Given
        when(newsMapper.patchUpdate(any(NewsEntity.class), any(UpdateNewsDto.class))).thenReturn(newsEntity);

        // When
        NewsEntity result = cacheNewsService.patchUpdateNews(newsEntity, updateNewsDto);

        // Then
        verify(newsMapper).patchUpdate(newsEntity, updateNewsDto);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Delete news successfully")
    public void testDeleteNews() {
        // Given
        doNothing().when(newsRepository).deleteById(anyLong());

        // When
        cacheNewsService.deleteNews(newsId);

        // Then
        verify(newsRepository).deleteById(newsId);
    }

    @Test
    @DisplayName("Get paginated news successfully")
    public void testGetPaginatedNews() {
        // Given
        NewsFilter newsFilter = NewsFilter.builder().build();
        newsFilter.setText("sample text");
        newsFilter.setTitle("sample title");
        Page<NewsEntity> newsPage = new PageImpl<>(List.of(newsEntity));

        when(newsRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(newsPage);
        doNothing().when(cacheNewsService).putAllNewsIntoCache(any());

        // When
        Page<NewsEntity> result = cacheNewsService.getPaginatedNews(0, 10, newsFilter);

        // Then
        verify(newsRepository).findAll(any(Specification.class), any(PageRequest.class));
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getTotalElements());
    }
}