package ru.clevertec.newsmanagementsystem.mapper;

import org.mapstruct.*;
import ru.clevertec.newsmanagementsystem.dto.news.CreateNewsDto;
import ru.clevertec.newsmanagementsystem.dto.news.CreatedNewsDto;
import ru.clevertec.newsmanagementsystem.dto.news.ExtendedNewsDto;
import ru.clevertec.newsmanagementsystem.dto.news.UpdateNewsDto;
import ru.clevertec.newsmanagementsystem.entity.NewsEntity;

import java.time.LocalDate;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class}
)
public interface NewsMapper {
    NewsEntity toEntity(CreateNewsDto createdNewsDto);

    @AfterMapping
    default void setCreationDate(NewsEntity createdNews){
        createdNews.setTime(LocalDate.now());
    }

    CreatedNewsDto toDto(NewsEntity newsEntity);

    ExtendedNewsDto toExtendedDto(NewsEntity newsEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NewsEntity patchUpdate(@MappingTarget NewsEntity newsEntity, UpdateNewsDto updateNewsDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "text", source = "text")
    NewsEntity fullUpdate(@MappingTarget NewsEntity newsEntity, UpdateNewsDto updateNewsDto);
}
