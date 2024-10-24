package ru.clevertec.newsmanagementsystem.mapper;

import org.mapstruct.*;
import ru.clevertec.newsmanagementsystem.dto.comment.CreateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.CreatedCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdateCommentDto;
import ru.clevertec.newsmanagementsystem.entity.CommentEntity;

import java.time.LocalDate;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {
    CreatedCommentDto toDto(CommentEntity comment);

    CommentEntity toEntity(CreateCommentDto createdCommentDto);

    @AfterMapping
    default void setCreationDate(CommentEntity createdComment){
        createdComment.setTime(LocalDate.now());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentEntity patchUpdate(@MappingTarget CommentEntity commentEntity, UpdateCommentDto createdCommentDto);
}
