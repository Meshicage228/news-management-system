package ru.clevertec.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.controller.doc.NewsControllerDoc;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.service.NewsService;
import ru.clevertec.loggingstarter.annotation.LogRequestResponse;

@RestController
@LogRequestResponse
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController implements NewsControllerDoc {
    private final NewsService newsService;

    @Override
    public Page<ShortNewsDto> getAllShortNews(Integer page,
                                              Integer size,
                                              NewsFilter newsFilter) {
        return newsService.getAllShortNews(page, size, newsFilter);
    }

    @Override
    public ExtendedNewsDto getNewsById(Integer page,
                                       Integer size,
                                       CommentFilter newsFilter) {
        return newsService.getNewsWithComments(page, size, newsFilter);
    }

    @Override
    public CreatedNewsDto createNews(CreateNewsDto createNewsDto) {
        return newsService.createNews(createNewsDto);
    }

    @Override
    public void deleteNewsById(Long newsId) {
        newsService.deleteNews(newsId);
    }

    @Override
    public UpdatedNewsDto fullUpdate(Long newsId,
                                     UpdateNewsDto updatedNewsDto) {
        return newsService.fullNewsUpdate(newsId, updatedNewsDto);
    }

    @Override
    public UpdatedNewsDto partUpdateNewsDto(Long newsId,
                                            UpdateNewsDto updatedNewsDto) {
        return newsService.partNewsUpdate(newsId, updatedNewsDto);
    }
}
