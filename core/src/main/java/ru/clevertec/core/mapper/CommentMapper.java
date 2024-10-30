package ru.clevertec.core.mapper;


import org.mapstruct.*;
import ru.clevertec.core.dto.comment.CreateCommentDto;
import ru.clevertec.core.dto.comment.CreatedCommentDto;
import ru.clevertec.core.dto.comment.UpdateCommentDto;
import ru.clevertec.core.dto.comment.UpdatedCommentDto;
import ru.clevertec.core.entity.CommentEntity;

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
