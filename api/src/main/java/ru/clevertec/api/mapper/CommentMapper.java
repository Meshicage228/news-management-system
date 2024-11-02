package ru.clevertec.api.mapper;


import org.mapstruct.*;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.entity.CommentEntity;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {
    CreatedCommentDto toDto(CommentEntity comment);

    CommentEntity toEntity(CreateCommentDto createdCommentDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentEntity patchUpdate(@MappingTarget CommentEntity commentEntity, UpdateCommentDto createdCommentDto);

    UpdatedCommentDto toUpdatedComment(CommentEntity commentEntity);
}
