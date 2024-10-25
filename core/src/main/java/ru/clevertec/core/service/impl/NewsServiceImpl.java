package ru.clevertec.core.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.core.dto.news.*;
import ru.clevertec.core.mapper.NewsMapper;
import ru.clevertec.core.repository.NewsRepository;
import ru.clevertec.core.service.NewsService;

import java.util.List;

@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public List<CreatedNewsDto> getAllNews(Integer pageNo, Integer pageSize) {
        return List.of();
    }

    @Override
    public CreatedNewsDto createNews(CreateNewsDto createNewsDto) {
        return null;
    }

    @Override
    public UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        return null;
    }

    @Override
    public UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        return null;
    }

    @Override
    public void deleteNews(Long id) {

    }

    @Override
    public ExtendedNewsDto getNewsWithComments(Long id) {
        return null;
    }
}
