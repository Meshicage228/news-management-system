package ru.clevertec.core.service;


import ru.clevertec.core.dto.news.*;

import java.util.List;

public interface NewsService {
    List<CreatedNewsDto> getAllNews(Integer pageNo, Integer pageSize);

    CreatedNewsDto createNews(CreateNewsDto createNewsDto);

    UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto);

    UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto);

    void deleteNews(Long id);

    ExtendedNewsDto getNewsWithComments(Long id);
}
