package ru.clevertec.api.mapper;

import org.mapstruct.*;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.entity.NewsEntity;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class}
)
public interface NewsMapper {
    NewsEntity toEntity(CreateNewsDto createdNewsDto);

    CreatedNewsDto toDto(NewsEntity newsEntity);

    @Mapping(target = "comments", ignore = true)
    ExtendedNewsDto toExtendedDto(NewsEntity newsEntity);

    ShortNewsDto toShortDto(NewsEntity newsEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NewsEntity patchUpdate(@MappingTarget NewsEntity newsEntity, UpdateNewsDto updateNewsDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "text", source = "text")
    NewsEntity fullUpdate(@MappingTarget NewsEntity newsEntity, UpdateNewsDto updateNewsDto);

    UpdatedNewsDto toUpdatedDto(NewsEntity newsEntity);
}
