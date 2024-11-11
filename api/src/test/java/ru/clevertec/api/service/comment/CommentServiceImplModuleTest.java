package ru.clevertec.api.service.comment;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.entity.NewsEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.service.impl.CommentServiceImpl;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.api.service.impl.cache.CacheNewsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Comment service module tests")
class CommentServiceImplModuleTest {
    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CacheCommentService cacheCommentService;

    @Mock
    private CacheNewsService cacheNewsService;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Long newsSource;
    private Long commentToUpdate;
    private CreateCommentDto createCommentDto;
    private UpdateCommentDto updateCommentDto;
    private NewsEntity newsEntity;
    private CommentEntity commentEntity;
    private CreatedCommentDto createdCommentDto;
    private UpdatedCommentDto updatedCommentDto;

    @BeforeEach
    public void setUp() {
        newsSource = 1L;
        commentToUpdate = 1L;
        createCommentDto = CreateCommentDto.builder().build();
        updateCommentDto = UpdateCommentDto.builder().build();
        newsEntity = new NewsEntity();
        commentEntity = new CommentEntity();
        createdCommentDto = new CreatedCommentDto();
        updatedCommentDto = new UpdatedCommentDto();
    }

    @Test
    @DisplayName("Create comment successfully")
    public void testCreateComment() {
        // Given
        when(cacheNewsService.getNewsById(anyLong())).thenReturn(newsEntity);
        when(commentMapper.toEntity(any(CreateCommentDto.class))).thenReturn(commentEntity);
        when(cacheCommentService.saveComment(any(CommentEntity.class))).thenReturn(commentEntity);
        when(commentMapper.toDto(any(CommentEntity.class))).thenReturn(createdCommentDto);

        // When
        CreatedCommentDto result = commentService.createComment(newsSource, createCommentDto);

        // Then
        verify(cacheNewsService).getNewsById(newsSource);
        verify(commentMapper).toEntity(createCommentDto);
        verify(cacheCommentService).saveComment(commentEntity);
        verify(commentMapper).toDto(commentEntity);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Part comment update")
    public void testPartCommentUpdate() {
        // Given
        when(cacheCommentService.getComment(anyLong())).thenReturn(commentEntity);
        when(cacheCommentService.partCommentUpdate(any(CommentEntity.class), any(UpdateCommentDto.class))).thenReturn(commentEntity);
        when(commentMapper.toUpdatedComment(any(CommentEntity.class))).thenReturn(updatedCommentDto);

        // When
        UpdatedCommentDto result = commentService.partCommentUpdate(commentToUpdate, updateCommentDto);

        // Then
        verify(cacheCommentService).getComment(commentToUpdate);
        verify(cacheCommentService).partCommentUpdate(commentEntity, updateCommentDto);
        verify(commentMapper).toUpdatedComment(commentEntity);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Comment not found while part update")
    public void commentNotFound() {
        // Given
        when(cacheCommentService.getComment(anyLong())).thenReturn(null);

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> commentService.partCommentUpdate(commentToUpdate, updateCommentDto));
    }

    @Test
    @DisplayName("Delete comment successfully")
    public void testDeleteComment() {
        // Given
        doNothing().when(cacheCommentService).deleteComment(anyLong());

        // When
        cacheCommentService.deleteComment(commentToUpdate);

        // Then
        verify(cacheCommentService).deleteComment(commentToUpdate);
    }
}