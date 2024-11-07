package ru.clevertec.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.api.config.PostgresContainerConfig;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.user.UserRequestDto;
import ru.clevertec.api.service.impl.TokenServiceImpl;
import ru.clevertec.api.util.PersistencePostgreSQLTests;

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
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenServiceImpl tokenService;

    @Test
    @DisplayName("Create comment successfully")
    void createComment() throws Exception {
        // Given
        CreatedCommentDto createdCommentDto = objectMapper.readValue(readFile("/comments/response/created-comment.json"), CreatedCommentDto.class);
        String request = readFile("/comments/request/create-comment.json");

        UserRequestDto userRequestDto = new UserRequestDto("Vlad", "111111");

        String username = "Vlad";
        String password = "111111";

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/v1/users/login"))
                .withQueryParam("username", WireMock.equalTo(username))
                .withQueryParam("password", WireMock.equalTo(password))
                .willReturn(WireMock.okJson(readFile("/user/admin.json"))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        String token = tokenService.createToken(userRequestDto);

        // When / Then
        mockMvc.perform(post("/api/v1/news/{newsId}/comments", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", token)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdCommentDto.getId()))
                .andExpect(jsonPath("$.text").value(createdCommentDto.getText()))
                .andExpect(jsonPath("$.authorName").value(createdCommentDto.getAuthorName()));
    }

//    @Test
//    @DisplayName("Partial update comment successfully")
//    void partialUpdateComment() throws Exception {
//        // Given
//        UpdatedCommentDto updatedCommentDto = objectMapper.readValue(readFile("/comments/response/updated-comment.json"), UpdatedCommentDto.class);
//        String request = readFile("/comments/request/update-comment.json");
//
//        // When / Then
//        mockMvc.perform(patch("/api/v1/news/{newsId}/comments/{commentsId}", newsId, commentId)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(request))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(updatedCommentDto.getId()))
//                .andExpect(jsonPath("$.text").value(updatedCommentDto.getText()));
//    }
//
//    @Test
//    @DisplayName("Delete comment successfully")
//    void deleteComment() throws Exception {
//        // Given
//
//        // When / Then
//        mockMvc.perform(delete("/api/v1/news/{newsId}/comments/{commentsId}", newsId, commentId))
//                .andExpect(status().isNoContent());
//    }
}