package ru.clevertec.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.service.NewsService;
import ru.clevertec.api.util.FullUpdateNewsMarker;
import ru.clevertec.loggingstarter.annotation.LogRequestResponse;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@LogRequestResponse
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public Page<ShortNewsDto> getAllShortNews(@RequestParam(defaultValue = "0", required = false, value = "page") Integer page,
                                              @RequestParam(defaultValue = "10", required = false, value = "size") Integer size,
                                              @RequestBody(required = false) NewsFilter newsFilter) {
        return newsService.getAllShortNews(page, size, newsFilter);
    }

    @GetMapping("/{newsId}")
    public ExtendedNewsDto getNewsById(@RequestParam(defaultValue = "0", required = false, value = "page") Integer page,
                                       @RequestParam(defaultValue = "10", required = false, value = "size") Integer size,
                                       @RequestBody(required = false) CommentFilter newsFilter) {
        return newsService.getNewsWithComments(page, size, newsFilter);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CreatedNewsDto createNews(@RequestBody @Valid CreateNewsDto createNewsDto) {
        return newsService.createNews(createNewsDto);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteNewsById(@PathVariable("newsId") Long newsId) {
        newsService.deleteNews(newsId);
    }

    @PutMapping("/{newsId}")
    public UpdatedNewsDto fullUpdate(@PathVariable("newsId") Long newsId,
                                     @RequestBody @Validated(FullUpdateNewsMarker.class) UpdateNewsDto updatedNewsDto) {
        return newsService.fullNewsUpdate(newsId, updatedNewsDto);
    }

    @PatchMapping("/{newsId}")
    public UpdatedNewsDto partUpdateNewsDto(@PathVariable("newsId") Long newsId,
                                            @RequestBody UpdateNewsDto updatedNewsDto) {
        return newsService.partNewsUpdate(newsId, updatedNewsDto);
    }
}
