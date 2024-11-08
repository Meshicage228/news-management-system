package ru.clevertec.api.service.news;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.mapper.NewsMapper;
import ru.clevertec.api.service.impl.NewsServiceImpl;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.api.service.impl.cache.CacheNewsService;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("News service module tests")
class NewsServiceImplModuleTest {
    @Mock
    private CacheNewsService cacheNewsService;

    @Mock
    private CacheCommentService cacheCommentService;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private NewsServiceImpl newsService;

    private Integer pageNo;
    private Integer pageSize;
    private NewsFilter filter;
    private Long newsId;
    private CommentFilter commentFilter;
    private CreateNewsDto createNewsDto;
    private UpdateNewsDto updateNewsDto;
    private NewsEntity newsEntity;
    private Page<NewsEntity> newsPage;
    private Page<CommentEntity> commentsPage;

    @BeforeEach
    public void setUp() {
        pageNo = 1;
        pageSize = 10;
        filter = NewsFilter.builder().build();
        newsId = 1L;
        commentFilter = CommentFilter.builder().build();
        commentFilter.setNewsId(newsId);
        createNewsDto = new CreateNewsDto();
        updateNewsDto = new UpdateNewsDto();
        newsEntity = new NewsEntity();
        newsPage = mock(Page.class);
        commentsPage = mock(Page.class);
    }

    @Test
    @DisplayName("get all short news")
    public void testGetAllShortNews() {
        // Given
        when(cacheNewsService.getPaginatedNews(anyInt(), anyInt(), any(NewsFilter.class)))
                .thenReturn(newsPage);
        when(newsPage.map(any())).thenAnswer(invocation -> {
            return new PageImpl<>(List.of(new ShortNewsDto(), new ShortNewsDto()));
        });

        // When
        Page<ShortNewsDto> result = newsService.getAllShortNews(pageNo, pageSize, filter);

        // Then
        verify(cacheNewsService).getPaginatedNews(pageNo, pageSize, filter);
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("get news with paginated comments")
    public void testGetNewsWithComments() {
        // Given
        when(cacheNewsService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(cacheCommentService.getPaginatedComments(any(CommentFilter.class), anyInt(), anyInt()))
                .thenReturn(commentsPage);
        when(commentsPage.map(any())).thenAnswer(invocation -> {
            return new PageImpl<>(List.of(new CreatedCommentDto(), new CreatedCommentDto()));
        });
        when(newsMapper.toExtendedDto(any(NewsEntity.class))).thenReturn(new ExtendedNewsDto());

        // When
        ExtendedNewsDto result = newsService.getNewsWithComments(pageNo, pageSize, commentFilter);

        // Then
        verify(cacheNewsService).getNewsById(newsId);
        verify(cacheCommentService).getPaginatedComments(commentFilter, pageNo, pageSize);
        verify(newsMapper).toExtendedDto(newsEntity);
        assertNotNull(result);
        assertEquals(2, result.getComments().getSize());
    }

    @Test
    @DisplayName("news not found exception")
    public void getNewsWithCommentsException() {
        // Given
        when(cacheNewsService.getNewsById(anyLong()))
                .thenThrow(NewsNotFoundException.class);

        // When & Then
        assertThrows(NewsNotFoundException.class, () -> newsService.getNewsWithComments(pageNo, pageSize, commentFilter));
        verify(cacheNewsService).getNewsById(newsId);
    }

    @Test
    @DisplayName("create news")
    public void testCreateNews() {
        // Given
        when(newsMapper.toEntity(any(CreateNewsDto.class))).thenReturn(newsEntity);
        when(cacheNewsService.saveNews(any(NewsEntity.class))).thenReturn(newsEntity);
        when(newsMapper.toDto(any(NewsEntity.class))).thenReturn(new CreatedNewsDto());

        // When
        CreatedNewsDto result = newsService.createNews(createNewsDto);

        // Then
        verify(newsMapper).toEntity(createNewsDto);
        verify(cacheNewsService).saveNews(newsEntity);
        assertNotNull(result);
    }

    @Test
    @DisplayName("full news update")
    public void testFullNewsUpdate() {
        // Given
        when(cacheNewsService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(cacheNewsService.fullUpdateNews(any(NewsEntity.class), any(UpdateNewsDto.class))).thenReturn(newsEntity);
        when(newsMapper.toUpdatedDto(any(NewsEntity.class))).thenReturn(new UpdatedNewsDto());

        // When
        UpdatedNewsDto result = newsService.fullNewsUpdate(newsId, updateNewsDto);

        // Then
        verify(cacheNewsService).getNewsById(newsId);
        verify(cacheNewsService).fullUpdateNews(newsEntity, updateNewsDto);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Part news update")
    public void testPartNewsUpdate() {
        // Given
        when(cacheNewsService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(cacheNewsService.patchUpdateNews(any(NewsEntity.class), any(UpdateNewsDto.class))).thenReturn(newsEntity);
        when(newsMapper.toUpdatedDto(any(NewsEntity.class))).thenReturn(new UpdatedNewsDto());

        // When
        UpdatedNewsDto result = newsService.partNewsUpdate(newsId, updateNewsDto);

        // Then
        verify(cacheNewsService).getNewsById(newsId);
        verify(cacheNewsService).patchUpdateNews(newsEntity, updateNewsDto);
        verify(newsMapper).toUpdatedDto(newsEntity);
        assertNotNull(result);
    }

    @Test
    public void testDeleteNews() {
        // Given
        Long newsId = 1L;
        NewsEntity newsEntity = new NewsEntity();
        CommentEntity comment1 = new CommentEntity();
        comment1.setId(1L);
        CommentEntity comment2 = new CommentEntity();
        comment2.setId(2L);
        newsEntity.setComments(Arrays.asList(comment1, comment2));
        when(cacheNewsService.getNewsById(anyLong())).thenReturn(newsEntity);
        doNothing().when(cacheCommentService).deleteComment(anyLong());
        doNothing().when(cacheNewsService).deleteNews(newsId);

        // When
        newsService.deleteNews(newsId);

        // Then
        verify(cacheNewsService).getNewsById(newsId);
        verify(cacheCommentService).deleteComment(comment1.getId());
        verify(cacheCommentService).deleteComment(comment2.getId());
        verify(cacheNewsService).deleteNews(newsId);
    }
}