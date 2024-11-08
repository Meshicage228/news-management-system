package ru.clevertec.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.api.util.annotation.PersistencePostgreSQLTests;
import ru.clevertec.api.util.wiremock.WireMockService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.api.util.FileReaderUtil.readFile;

@WireMockTest(httpPort = 7777)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PersistencePostgreSQLTests
@AutoConfigureMockMvc
class CommentsControllerE2ETest {
    private static final Long newsId = 1L;
    private static final Long commentId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private WireMockService wireMockService;

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "SUBSCRIBER"})
    @DisplayName("Create comment successfully with roles")
    void createComment(String role) throws Exception {
        // Given
        CreatedCommentDto createdCommentDto = objectMapper.readValue(readFile("/comments/response/created-comment.json"), CreatedCommentDto.class);
        String request = readFile("/comments/request/create-comment.json");

        String token = wireMockService.setupUserAndGetToken("Vlad", "111111", role);

        // When / Then
        mockMvc.perform(post("/api/v1/news/{newsId}/comments", newsId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", String.format("Bearer %s", token))
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdCommentDto.getId()))
                .andExpect(jsonPath("$.text").value(createdCommentDto.getText()))
                .andExpect(jsonPath("$.authorName").value(createdCommentDto.getAuthorName()));

        assertEquals(2, commentsRepository.count());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "SUBSCRIBER"})
    @DisplayName("Deletes comment successfully with roles")
    void deleteComment(String role) throws Exception {
        // Given
        String token = wireMockService.setupUserAndGetToken("Vlad", "111111", role);

        // When / Then
        mockMvc.perform(delete("/api/v1/news/{newsId}/comments/{commentId}", newsId, commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", String.format("Bearer %s", token)))
                .andExpect(status().isNoContent());

        assertEquals(0, commentsRepository.count());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "SUBSCRIBER"})
    @DisplayName("Deletes comment successfully with roles")
    void partUpdateComment(String role) throws Exception {
        // Given
        CreatedCommentDto createdCommentDto = objectMapper.readValue(readFile("/comments/response/updated-comment.json"), CreatedCommentDto.class);
        String request = readFile("/comments/request/update-comment.json");
        String token = wireMockService.setupUserAndGetToken("Vlad", "111111", role);

        // When / Then
        mockMvc.perform(patch("/api/v1/news/{newsId}/comments/{commentId}", newsId, commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", String.format("Bearer %s", token))
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCommentDto.getId()))
                .andExpect(jsonPath("$.text").value(createdCommentDto.getText()))
                .andExpect(jsonPath("$.authorName").value(createdCommentDto.getAuthorName()));
    }
}