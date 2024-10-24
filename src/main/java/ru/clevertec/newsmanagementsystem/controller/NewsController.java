package ru.clevertec.newsmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsmanagementsystem.dto.news.*;
import ru.clevertec.newsmanagementsystem.service.NewsService;
import ru.clevertec.newsmanagementsystem.util.FullUpdateNewsMarker;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private NewsService newsService;

    @GetMapping
    public List<CreatedNewsDto> getAllNews(@RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size) {
        return newsService.getAllNews(page, size);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CreatedNewsDto createNews(@RequestBody @Valid CreateNewsDto createNewsDto) {
        return newsService.createNews(createNewsDto);
    }

    @GetMapping("/{newsId}")
    public ExtendedNewsDto getNewsById(@PathVariable Long newsId) {
        return newsService.getNewsWithComments(newsId);
    }

    @DeleteMapping("/{newsId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteNewsById(@PathVariable Long newsId) {
        newsService.deleteNews(newsId);
    }

    @PutMapping("/{newsId}")
    public UpdatedNewsDto fullUpdate(@PathVariable Long newsId,
                                     @RequestBody @Validated(FullUpdateNewsMarker.class) UpdateNewsDto updatedNewsDto) {
        return newsService.fullNewsUpdate(newsId, updatedNewsDto);
    }

    @PatchMapping("/{newsId}")
    public UpdatedNewsDto partUpdateNewsDto(@PathVariable Long newsId,
                                            @RequestBody UpdateNewsDto updatedNewsDto) {
        return newsService.partNewsUpdate(newsId, updatedNewsDto);
    }
}
