package ru.clevertec.api.service.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.globalexceptionhandlingstarter.exception.comment.CommentNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CacheCommentService module tests")
class CacheCommentServiceTest {
    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CacheCommentService cacheCommentService;

    private Long commentId;
    private CommentEntity commentEntity;
    private UpdateCommentDto updateCommentDto;

    @BeforeEach
    public void setUp() {
        commentId = 1L;
        commentEntity = new CommentEntity();
        commentEntity.setId(commentId);
        updateCommentDto = new UpdateCommentDto();
    }

    @Test
    @DisplayName("Get comment by ID successfully")
    public void testGetCommentById() {
        // Given
        when(commentsRepository.findById(anyLong())).thenReturn(Optional.of(commentEntity));

        // When
        CommentEntity result = cacheCommentService.getComment(commentId);

        // Then
        verify(commentsRepository).findById(commentId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Get comment by ID not found")
    public void testGetCommentByIdNotFound() {
        // Given
        when(commentsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(CommentNotFoundException.class, () -> cacheCommentService.getComment(commentId));
    }

    @Test
    @DisplayName("Save comment successfully")
    public void testSaveComment() {
        // Given
        when(commentsRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

        // When
        CommentEntity result = cacheCommentService.saveComment(commentEntity);

        // Then
        verify(commentsRepository).save(commentEntity);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Partially update comment successfully")
    public void testPartCommentUpdate() {
        // Given
        when(commentMapper.patchUpdate(any(CommentEntity.class), any(UpdateCommentDto.class))).thenReturn(commentEntity);

        // When
        CommentEntity result = cacheCommentService.partCommentUpdate(commentEntity, updateCommentDto);

        // Then
        verify(commentMapper).patchUpdate(commentEntity, updateCommentDto);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Delete comment successfully")
    public void testDeleteComment() {
        // Given
        doNothing().when(commentsRepository).deleteById(anyLong());

        // When
        cacheCommentService.deleteComment(commentId);

        // Then
        verify(commentsRepository).deleteById(commentId);
    }

    @Test
    @DisplayName("Get paginated comments successfully")
    public void testGetPaginatedComments() {
        // Given
        CommentFilter commentFilter = CommentFilter.builder().build();
        commentFilter.setNewsId(1L);
        Page<CommentEntity> commentPage = new PageImpl<>(List.of(commentEntity));

        when(commentsRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(commentPage);

        // When
        Page<CommentEntity> result = cacheCommentService.getPaginatedComments(commentFilter, 0, 10);

        // Then
        verify(commentsRepository).findAll(any(Specification.class), any(PageRequest.class));
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getTotalElements());
    }
}