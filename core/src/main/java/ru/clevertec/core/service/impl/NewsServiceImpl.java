package ru.clevertec.core.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.core.dto.news.*;
import ru.clevertec.core.exception.FailedToCreateNewsException;
import ru.clevertec.core.exception.NewsNotFoundException;
import ru.clevertec.core.mapper.NewsMapper;
import ru.clevertec.core.repository.NewsRepository;
import ru.clevertec.core.service.NewsService;

import java.util.List;
import java.util.Optional;

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
        return Optional.of(createNewsDto)
                .map(newsMapper::toEntity)
                .map(newsRepository::save)
                .map(newsMapper::toDto)
                .orElseThrow(FailedToCreateNewsException::new);
    }

    @Override
    public UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        return Optional.of(newsRepository.getReferenceById(entityToUpdate))
                .map(newsEntity -> newsMapper.fullUpdate(newsEntity, updateNewsDto))
                .map(newsMapper::toUpdatedDto)
                .orElseThrow(() -> new NewsNotFoundException(entityToUpdate));
    }

    @Override
    public UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        return Optional.of(newsRepository.getReferenceById(entityToUpdate))
                .map(newsEntity -> newsMapper.patchUpdate(newsEntity, updateNewsDto))
                .map(newsMapper::toUpdatedDto)
                .orElseThrow(() -> new NewsNotFoundException(entityToUpdate));
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public ExtendedNewsDto getNewsWithComments(Long id) {
        return Optional.of(newsRepository.getReferenceById(id))
                .map(newsMapper::toExtendedDto)
                .orElseThrow(() -> new NewsNotFoundException(id));
    }
}
