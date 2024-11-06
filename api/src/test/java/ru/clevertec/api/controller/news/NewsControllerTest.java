package ru.clevertec.api.controller.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.api.ApiApplication;
import ru.clevertec.api.config.TestSecurityConfig;
import ru.clevertec.api.controller.NewsController;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.service.impl.NewsServiceImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.api.util.FileReaderUtil.readFile;

@ContextConfiguration(classes = {TestSecurityConfig.class, ApiApplication.class})
@WebMvcTest(controllers = NewsController.class)
@ActiveProfiles("test")
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
        String request = readFile("/news/request/create-news-request.json");

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
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.time").value(responseDto.getTime().toString()));
    }

//    @Test
//    @DisplayName("Get all short news successfully")
//    void getAllShortNews() throws Exception {
//        // Given
//        String expectedResult = readFile("/news/response/short-news.json");
//        ShortNewsDto[] shortNewsDtos = objectMapper.readValue(expectedResult, ShortNewsDto[].class);
//
//        Page<ShortNewsDto> page = new PageImpl<>(List.of(shortNewsDtos), PageRequest.of(0, 10), shortNewsDtos.length);
//
//        when(newsService.getAllShortNews(anyInt(), anyInt(), any(NewsFilter.class)))
//                .thenReturn(page);
//
//        // When / Then
//        mockMvc.perform(get("/api/v1/news")
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.content").isNotEmpty());
//    }
//
//    @Test
//    @DisplayName("Get news by ID successfully")
//    void getNewsById() throws Exception {
//        // Given
//        CreatedNewsDto responseDto = objectMapper.readValue(readFile("/news/saved-news.json"), CreatedNewsDto.class);
//
//        when(newsService.getNewsWithComments(anyInt(), anyInt(), any(CommentFilter.class)))
//                .thenReturn(null);
//
//        // When / Then
//        mockMvc.perform(get("/api/v1/news/{id}", 1)
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(2));
//    }

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
        String request = readFile("/news/request/full-update-news-request.json");

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