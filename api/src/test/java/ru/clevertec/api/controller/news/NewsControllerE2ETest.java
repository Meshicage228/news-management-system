package ru.clevertec.api.controller.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.api.ApiApplication;
import ru.clevertec.api.config.TestSecurityConfig;
import ru.clevertec.api.config.WireMockConfig;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.CreatedNewsDto;
import ru.clevertec.api.dto.news.ExtendedNewsDto;
import ru.clevertec.api.dto.news.ShortNewsDto;
import ru.clevertec.api.dto.news.UpdatedNewsDto;
import ru.clevertec.api.repository.NewsRepository;
import ru.clevertec.api.util.annotation.PersistencePostgreSQLTests;
import ru.clevertec.api.util.wiremock.WireMockService;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.api.util.FileReaderUtil.readFile;

@WireMockTest(httpPort = 7777)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ApiApplication.class, WireMockConfig.class})
@PersistencePostgreSQLTests
@AutoConfigureMockMvc
class NewsControllerE2ETest {
    private static final Long newsId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TestSecurityConfig testSecurityConfig;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private WireMockService wireMockService;

    @ParameterizedTest
    @MethodSource("getFilters")
    @DisplayName("Get shorted news successfully using filters")
    void getAllShortedNews(NewsFilter newsFilter) throws Exception {
        // Given
        String request = objectMapper.writeValueAsString(newsFilter);
        ShortNewsDto[] shortNewsDtos = objectMapper.readValue(readFile("/news/response/short-news.json"), ShortNewsDto[].class);
        ShortNewsDto shortNewsDto = shortNewsDtos[0];
        // When / Then
        mockMvc.perform(get("/api/v1/news")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(shortNewsDto.getId()))
                .andExpect(jsonPath("$.content[0].text").value(shortNewsDto.getText()))
                .andExpect(jsonPath("$.content[0].time").value(shortNewsDto.getTime().toString()))
                .andExpect(jsonPath("$.content[0].title").value(shortNewsDto.getTitle()));
    }

    public static Stream<Arguments> getFilters() {
        return Stream.of(
                Arguments.of(NewsFilter.builder().text("News test text").build()),
                Arguments.of(NewsFilter.builder().title("Test news").build())
        );
    }

    @ParameterizedTest
    @MethodSource("getCommentFilters")
    @DisplayName("Get news by id with commentary filters")
    void getNewsByIdUsingCommentaryFilters(CommentFilter newsFilter) throws Exception {
        // Given
        String request = objectMapper.writeValueAsString(newsFilter);
        ExtendedNewsDto extendedNewsDto = objectMapper.readValue(readFile("/news/response/news-by-id.json"), ExtendedNewsDto.class);

        // When / Then
        mockMvc.perform(get("/api/v1/news/comments")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(extendedNewsDto.getId()))
                .andExpect(jsonPath("$.authorName").value(extendedNewsDto.getAuthorName()));
    }

    public static Stream<Arguments> getCommentFilters() {
        return Stream.of(
                Arguments.of(CommentFilter.builder().newsId(newsId).text("News test text").build()),
                Arguments.of(CommentFilter.builder().newsId(newsId).authorName("Vlad").build())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "JOURNALIST"})
    @DisplayName("Create news successfully : with roles")
    void createNews(String role) throws Exception {
        // Given
        CreatedNewsDto createdNewsDto = objectMapper.readValue(readFile("/news/response/saved-news.json"), CreatedNewsDto.class);
        String request = readFile("/news/request/create-news.json");
        String token = wireMockService.setupUserAndGetToken("Vlad", "111111", role);

        // When / Then
        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", String.format("Bearer %s", token))
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdNewsDto.getId()))
                .andExpect(jsonPath("$.text").value(createdNewsDto.getText()))
                .andExpect(jsonPath("$.authorName").value(createdNewsDto.getAuthorName()))
                .andExpect(jsonPath("$.title").value(createdNewsDto.getTitle()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "JOURNALIST"})
    @DisplayName("Full update news successfully : with roles")
    void fullUpdate(String role) throws Exception {
        // Given
        UpdatedNewsDto updatedNewsDto = objectMapper.readValue(readFile("/news/response/full-updated-news.json"), UpdatedNewsDto.class);
        String request = readFile("/news/request/full-update-news.json");
        String token = wireMockService.setupUserAndGetToken("Vlad", "111111", role);

        // When / Then
        mockMvc.perform(put("/api/v1/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", String.format("Bearer %s", token))
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value(updatedNewsDto.getAuthorName()))
                .andExpect(jsonPath("$.text").value(updatedNewsDto.getText()))
                .andExpect(jsonPath("$.title").value(updatedNewsDto.getTitle()))
                .andExpect(jsonPath("$.id").value(updatedNewsDto.getId()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "JOURNALIST"})
    @DisplayName("Patch update news successfully : with roles")
    void pathUpdate(String role) throws Exception {
        // Given
        Long newsId = 1L;
        UpdatedNewsDto updatedNewsDto = objectMapper.readValue(readFile("/news/response/patch-updated-news.json"), UpdatedNewsDto.class);
        String request = readFile("/news/request/patch-update-news.json");
        String token = wireMockService.setupUserAndGetToken("Vlad", "111111", role);

        // When / Then
        mockMvc.perform(patch("/api/v1/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", String.format("Bearer %s", token))
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(updatedNewsDto.getText()));
    }
}