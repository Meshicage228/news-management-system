package ru.clevertec.api.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.mapper.CommentMapper;
import ru.clevertec.api.mapper.NewsMapper;
import ru.clevertec.api.repository.CommentsRepository;
import ru.clevertec.api.repository.NewsRepository;
import ru.clevertec.api.service.NewsService;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.FailedToCreateNewsException;
import ru.clevertec.globalexceptionhandlingstarter.exception.news.NewsNotFoundException;

import java.util.Optional;

import static ru.clevertec.api.repository.specification.CommentSpecificationService.createCommentSpecification;
import static ru.clevertec.api.repository.specification.NewsSpecificationService.createNewsSpecification;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;
    private final NewsMapper newsMapper;

    @Override
    public Page<ShortNewsDto> getAllShortNews(Integer pageNo, Integer pageSize, NewsFilter filter) {
        log.info("Get all short news");
        return newsRepository.findAll(createNewsSpecification(filter), PageRequest.of(pageNo, pageSize))
                .map(newsMapper::toShortDto);
    }

    @Override
    public ExtendedNewsDto getNewsWithComments(Integer pageNo, Integer pageSize, CommentFilter commentFilter) {
        log.info("Get new with comments");
        Page<CreatedCommentDto> commentDtos = commentsRepository
                .findAll(createCommentSpecification(commentFilter), PageRequest.of(pageNo, pageSize))
                .map(commentMapper::toDto);

        ExtendedNewsDto extendedNewsDto = Optional.of(newsRepository.getReferenceById(commentFilter.getNewsId()))
                .map(newsMapper::toExtendedDto)
                .orElseThrow(() -> new NewsNotFoundException(commentFilter.getNewsId()));

        extendedNewsDto.setComments(commentDtos);

        return extendedNewsDto;
    }

    @Override
    public CreatedNewsDto createNews(CreateNewsDto createNewsDto) {
        log.info("Create new news on base of : {}", createNewsDto);
        return Optional.of(createNewsDto)
                .map(newsMapper::toEntity)
                .map(newsRepository::save)
                .map(newsMapper::toDto)
                .orElseThrow(FailedToCreateNewsException::new);
    }

    @Override
    @Transactional
    public UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        log.info("Full new update for : {} on base of : {}", entityToUpdate ,updateNewsDto);
        return Optional.of(newsRepository.getReferenceById(entityToUpdate))
                .map(newsEntity -> newsMapper.fullUpdate(newsEntity, updateNewsDto))
                .map(newsMapper::toUpdatedDto)
                .orElseThrow(() -> new NewsNotFoundException(entityToUpdate));
    }

    @Override
    @Transactional
    public UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto) {
        log.info("Part new update for : {} on base of : {}", entityToUpdate ,updateNewsDto);
        return Optional.of(newsRepository.getReferenceById(entityToUpdate))
                .map(newsEntity -> newsMapper.patchUpdate(newsEntity, updateNewsDto))
                .map(newsMapper::toUpdatedDto)
                .orElseThrow(() -> new NewsNotFoundException(entityToUpdate));
    }

    @Override
    public void deleteNews(Long id) {
        log.info("Delete news : {}", id);
        newsRepository.deleteById(id);
    }
}
