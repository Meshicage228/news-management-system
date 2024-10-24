package ru.clevertec.newsmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.newsmanagementsystem.dto.news.*;
import ru.clevertec.newsmanagementsystem.mapper.NewsMapper;
import ru.clevertec.newsmanagementsystem.repository.NewsRepository;
import ru.clevertec.newsmanagementsystem.service.NewsService;

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
