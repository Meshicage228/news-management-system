package ru.clevertec.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.clevertec.api.config.TestSecurityConfig;
import ru.clevertec.api.controller.CommentsController;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.service.impl.CommentServiceImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.api.util.FileReaderUtil.readFile;

@SpringBootTest(classes = {CommentsController.class, JacksonAutoConfiguration.class, TestSecurityConfig.class})
@EnableWebMvc
@AutoConfigureMockMvc
@DisplayName("Mock comments controller test")
class CommentsControllerTest {
    private static final Long newsId = 1L;
    private static final Long commentId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("Create comment successfully")
    void createComment() throws Exception {
        // Given
        CreatedCommentDto createdCommentDto = objectMapper.readValue(readFile("/comments/response/created-comment.json"), CreatedCommentDto.class);
        String request = readFile("/comments/request/create-comment.json");

        when(commentService.createComment(anyLong(), any(CreateCommentDto.class)))
                .thenReturn(createdCommentDto);

        // When / Then
        mockMvc.perform(post("/api/v1/news/{newsId}/comments", newsId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdCommentDto.getId()))
                .andExpect(jsonPath("$.text").value(createdCommentDto.getText()))
                .andExpect(jsonPath("$.authorName").value(createdCommentDto.getAuthorName()));
    }

    @Test
    @DisplayName("Partial update comment successfully")
    void partialUpdateComment() throws Exception {
        // Given
        UpdatedCommentDto updatedCommentDto = objectMapper.readValue(readFile("/comments/response/updated-comment.json"), UpdatedCommentDto.class);
        String request = readFile("/comments/request/update-comment.json");

        when(commentService.partCommentUpdate(anyLong(), any(UpdateCommentDto.class)))
                .thenReturn(updatedCommentDto);

        // When / Then
        mockMvc.perform(patch("/api/v1/news/{newsId}/comments/{commentsId}", newsId, commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCommentDto.getId()))
                .andExpect(jsonPath("$.text").value(updatedCommentDto.getText()));
    }

    @Test
    @DisplayName("Delete comment successfully")
    void deleteComment() throws Exception {
        // Given
        doNothing().when(commentService).deleteComment(anyLong(), anyLong());

        // When / Then
        mockMvc.perform(delete("/api/v1/news/{newsId}/comments/{commentsId}", newsId, commentId))
                .andExpect(status().isNoContent());
    }
}