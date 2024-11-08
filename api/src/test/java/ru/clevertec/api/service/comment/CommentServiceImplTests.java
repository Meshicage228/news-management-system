package ru.clevertec.api.service.comment;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.entity.CommentEntity;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.api.service.impl.CommentServiceImpl;
import ru.clevertec.api.service.impl.cache.CacheCommentService;
import ru.clevertec.api.util.annotation.PersistencePostgreSQLTests;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@PersistencePostgreSQLTests
@DisplayName("Comment integrated tests")
public class CommentServiceImplTests {
    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private CacheCommentService cacheCommentService;

    @Autowired
    private CommentsRepository commentsRepository;

    private Long newsId;
    private Long commentId;
    private CreateCommentDto createCommentDto;

    @BeforeEach
    public void setUp() {
        commentId = 1L;
        newsId = 1L;
        createCommentDto = CreateCommentDto.builder()
                .authorName("Test author")
                .text("Test text")
                .build();
    }

    @Test
    public void testCreateComment() {
        CreatedCommentDto createdCommentDto = commentService.createComment(newsId, createCommentDto);

        Optional<CommentEntity> commentFromDb = commentsRepository.findById(createdCommentDto.getId());
        assertTrue(commentFromDb.isPresent());
        assertEquals(createdCommentDto.getId(), commentFromDb.get().getId());
    }

    @Test
    @Transactional
    public void testUpdateComment() {
        String updatedText = "Text updated";
        UpdateCommentDto updateCommentDto = UpdateCommentDto.builder()
                .text(updatedText)
                .build();
        UpdatedCommentDto updatedCommentDto = commentService.partCommentUpdate(commentId, updateCommentDto);

        Optional<CommentEntity> commentFromDb = commentsRepository.findById(updatedCommentDto.getId());
        assertTrue(commentFromDb.isPresent());
        assertEquals(commentId, commentFromDb.get().getId());
        assertEquals(updatedText, commentFromDb.get().getText());
    }

    @Test
    public void testDeleteComment() {
        commentService.deleteComment(newsId, commentId);

        assertEquals(commentsRepository.findAll().size(), 0);
    }
}
