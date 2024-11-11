package ru.clevertec.api.controller.news;

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
import ru.clevertec.api.controller.NewsController;
import ru.clevertec.api.config.TestSecurityConfig;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.service.impl.NewsServiceImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.api.util.FileReaderUtil.readFile;

@SpringBootTest(classes = {NewsController.class, JacksonAutoConfiguration.class, TestSecurityConfig.class})
@EnableWebMvc
@AutoConfigureMockMvc
@DisplayName("Mock news controller test")
class NewsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsServiceImpl newsService;

    @Test
    @DisplayName("create news successfully")
    void createNews() throws Exception {
        // Given
        CreatedNewsDto responseDto = objectMapper.readValue(readFile("/news/response/saved-news.json"), CreatedNewsDto.class);
        String request = readFile("/news/request/create-news.json");

        when(newsService.createNews(any(CreateNewsDto.class)))
                .thenReturn(responseDto);

        //When / then
        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.authorName").value(responseDto.getAuthorName()))
                .andExpect(jsonPath("$.text").value(responseDto.getText()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.id").value(responseDto.getId()));
    }

    @Test
    @DisplayName("Delete news by ID successfully")
    void deleteNewsById() throws Exception {
        // Given
        Long newsId = 1L;
        doNothing().when(newsService).deleteNews(anyLong());

        // When / Then
        mockMvc.perform(delete("/api/v1/news/{id}", newsId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Full update news successfully")
    void fullUpdate() throws Exception {
        // Given
        Long newsId = 1L;
        UpdatedNewsDto updatedNewsDto = objectMapper.readValue(readFile("/news/response/saved-news.json"), UpdatedNewsDto.class);
        String request = readFile("/news/request/full-update-news.json");

        when(newsService.fullNewsUpdate(anyLong(), any(UpdateNewsDto.class)))
                .thenReturn(updatedNewsDto);

        // When / Then
        mockMvc.perform(put("/api/v1/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value(updatedNewsDto.getAuthorName()))
                .andExpect(jsonPath("$.text").value(updatedNewsDto.getText()))
                .andExpect(jsonPath("$.title").value(updatedNewsDto.getTitle()))
                .andExpect(jsonPath("$.id").value(updatedNewsDto.getId()));
    }

    @Test
    @DisplayName("Patch update news successfully")
    void pathUpdate() throws Exception {
        // Given
        Long newsId = 1L;
        UpdatedNewsDto updatedNewsDto = objectMapper.readValue(readFile("/news/response/patch-updated-news.json"), UpdatedNewsDto.class);
        String request = readFile("/news/request/patch-update-news.json");

        when(newsService.partNewsUpdate(anyLong(), any(UpdateNewsDto.class)))
                .thenReturn(updatedNewsDto);

        // When / Then
        mockMvc.perform(patch("/api/v1/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedNewsDto.getTitle()));
    }
}