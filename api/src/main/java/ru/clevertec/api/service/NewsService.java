package ru.clevertec.api.service;

import org.springframework.data.domain.Page;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;

/**
 * Интерфейс сервиса для работы с новостями.
 * Обеспечивает операции получения, создания, обновления и удаления новостей.
 */
public interface NewsService {

    /**
     * Получает все короткие новости с постраничной навигацией.
     *
     * @param pageNo Номер страницы.
     * @param pageSize Размер страницы.
     * @param filter Фильтр для поиска новостей.
     * @return Страница коротких новостей.
     */
    Page<ShortNewsDto> getAllShortNews(Integer pageNo, Integer pageSize, NewsFilter filter);

    /**
     * Создает новую новость.
     *
     * @param createNewsDto Объект с данными для создания новости.
     * @return Объект, представляющий созданную новость.
     */
    CreatedNewsDto createNews(CreateNewsDto createNewsDto);

    /**
     * Полностью обновляет существующую новость.
     *
     * @param entityToUpdate Идентификатор новости, которую нужно обновить.
     * @param updateNewsDto Объект с данными для обновления новости.
     * @return Объект, представляющий обновленную новость.
     */
    UpdatedNewsDto fullNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto);

    /**
     * Частично обновляет существующую новость.
     *
     * @param entityToUpdate Идентификатор новости, которую нужно частично обновить.
     * @param updateNewsDto Объект с данными для частичного обновления новости.
     * @return Объект, представляющий обновленную новость.
     */
    UpdatedNewsDto partNewsUpdate(Long entityToUpdate, UpdateNewsDto updateNewsDto);

    /**
     * Удаляет новость по указанному идентификатору.
     *
     * @param id Идентификатор новости, которую нужно удалить.
     */
    void deleteNews(Long id);

    /**
     * Получает новость с комментариями и постраничной навигацией для комментариев.
     *
     * @param pageNo Номер страницы для комментариев.
     * @param pageSize Размер страницы для комментариев.
     * @param commentFilter Фильтр для поиска комментариев.
     * @return Объект, представляющий новость с комментариями.
     */
    ExtendedNewsDto getNewsWithComments(Integer pageNo, Integer pageSize, CommentFilter commentFilter);
}
