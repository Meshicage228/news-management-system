package ru.clevertec.api.mapper;

import org.mapstruct.*;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.entity.NewsEntity;

/**
 * Mapper для преобразования между сущностями новостей и DTO.
 *
 * <p>
 * Этот интерфейс использует MapStruct для автоматического преобразования между
 * {@link NewsEntity} и различными DTO, такими как {@link CreateNewsDto}, {@link CreatedNewsDto} и {@link UpdatedNewsDto}.
 * Также используется {@link CommentMapper} для обработки комментариев, связанных с новостями.
 * </p>
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class}
)
public interface NewsMapper {

    /**
     * Преобразует входные данные из DTO в сущность новости для дальнейшего сохранения.
     *
     * @param createdNewsDto DTO, содержащее информацию для создания новости.
     * @return Сущность новости, созданная на основе переданного DTO.
     */
    NewsEntity toEntity(CreateNewsDto createdNewsDto);

    /**
     * Преобразует сущность новости в DTO для отображения.
     *
     * @param newsEntity Сущность новости, которую необходимо преобразовать.
     * @return Созданная новость.
     */
    CreatedNewsDto toDto(NewsEntity newsEntity);

    /**
     * Преобразует сущность новости в расширенное DTO, игнорируя комментарии.
     *
     * @param newsEntity Сущность новости, которую необходимо преобразовать.
     * @return Расширенное DTO, представляющее новость.
     */
    @Mapping(target = "comments", ignore = true)
    ExtendedNewsDto toExtendedDto(NewsEntity newsEntity);

    /**
     * Преобразует сущность новости в сокращенное DTO.
     *
     * @param newsEntity Сущность новости, которую необходимо преобразовать.
     * @return Сокращенное DTO, представляющее новость.
     */
    ShortNewsDto toShortDto(NewsEntity newsEntity);

    /**
     * Частично обновляет существующую сущность новости с использованием переданных данных из DTO.
     *
     * <p>
     * Поля, которые равны null в {@link UpdateNewsDto}, будут игнорироваться при обновлении.
     * </p>
     *
     * @param newsEntity Сущность новости, которую необходимо обновить.
     * @param updateNewsDto DTO, содержащее данные для обновления.
     * @return Обновленная сущность новости.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NewsEntity patchUpdate(@MappingTarget NewsEntity newsEntity, UpdateNewsDto updateNewsDto);

    /**
     * Полностью обновляет сущность новости с использованием данных из DTO.
     *
     * <p>
     * Игнорирует все поля по умолчанию и обновляет только указанные поля.
     * </p>
     *
     * @param newsEntity Сущность новости, которую необходимо обновить.
     * @param updateNewsDto DTO, содержащий данные для обновления.
     * @return Обновленная сущность новости.
     */
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "text", source = "text")
    NewsEntity fullUpdate(@MappingTarget NewsEntity newsEntity, UpdateNewsDto updateNewsDto);

    /**
     * Преобразует сущность новости в DTO, представляющее обновленную новость.
     *
     * @param newsEntity Сущность новости, которую необходимо преобразовать.
     * @return DTO, представляющее обновленную новость.
     */
    UpdatedNewsDto toUpdatedDto(NewsEntity newsEntity);
}
