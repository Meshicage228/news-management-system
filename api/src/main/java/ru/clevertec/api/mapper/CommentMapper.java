package ru.clevertec.api.mapper;


import org.mapstruct.*;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.entity.CommentEntity;

/**
 * Mapper для преобразования между сущностями комментариев и DTO.
 *
 * <p>
 * Этот интерфейс использует MapStruct для автоматического преобразования между
 * {@link CommentEntity} и различными DTO, такими как {@link CreatedCommentDto} и {@link UpdatedCommentDto}.
 * </p>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    /**
     * Преобразует созданную сущность комментария в DTO для отображения.
     *
     * @param comment Сущность комментария, которую необходимо преобразовать.
     * @return Созданный комментарий.
     */
    CreatedCommentDto toDto(CommentEntity comment);

    /**
     * Преобразует входные данные из DTO в сущность комментария для дальнейшего сохранения.
     *
     * @param createdCommentDto DTO, содержащее информацию для создания комментария.
     * @return Сущность комментария, созданная на основе переданного DTO.
     */
    CommentEntity toEntity(CreateCommentDto createdCommentDto);

    /**
     * Частично обновляет существующую сущность комментария с использованием переданных данных из DTO.
     *
     * <p>
     * Поля, которые равны null в {@link UpdateCommentDto}, будут игнорироваться при обновлении.
     * </p>
     *
     * @param commentEntity Сущность комментария, которую необходимо обновить.
     * @param createdCommentDto DTO, содержащее данные для обновления.
     * @return Обновленная сущность комментария.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentEntity patchUpdate(@MappingTarget CommentEntity commentEntity, UpdateCommentDto createdCommentDto);

    /**
     * Преобразует сущность комментария в DTO, представляющее обновленный комментарий.
     *
     * @param commentEntity Сущность комментария, которую необходимо преобразовать.
     * @return DTO, представляющее обновленный комментарий.
     */
    UpdatedCommentDto toUpdatedComment(CommentEntity commentEntity);
}
