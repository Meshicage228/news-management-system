package ru.clevertec.core.service;

import org.springframework.data.domain.Page;
import ru.clevertec.core.dto.filter.CommentFilter;
import ru.clevertec.core.dto.news.*;
import ru.clevertec.core.dto.filter.NewsFilter;

public interface NewsService {
    Page<ShortNewsDto> getAllShortNews(Integer pageNo, Integer pageSize, NewsFilter filter);

    CreatedNewsDto createNews(CreateNewsDto createNewsDto);

    UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto);

    UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto);

    void deleteNews(Long id);

    ExtendedNewsDto getNewsWithComments(Integer pageNo, Integer pageSize, CommentFilter commentFilter);
}
