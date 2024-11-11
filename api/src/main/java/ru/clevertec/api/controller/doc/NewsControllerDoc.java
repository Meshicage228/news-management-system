package ru.clevertec.api.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.dto.filter.CommentFilter;
import ru.clevertec.api.dto.filter.NewsFilter;
import ru.clevertec.api.dto.news.*;
import ru.clevertec.api.util.FullUpdateNewsMarker;
import ru.clevertec.globalexceptionhandlingstarter.dto.ExceptionResponse;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public interface NewsControllerDoc {
    @Operation(summary = "Получить все краткие новости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены краткие новости"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping
    Page<ShortNewsDto> getAllShortNews(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0", required = false, value = "page") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(defaultValue = "10", required = false, value = "size") Integer size,
            @Parameter(description = "Фильтр новостей")
            @RequestBody(required = false) NewsFilter newsFilter
    );

    @Operation(summary = "Получить новость по ID с комментариями")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получена новость с комментариями"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/comments")
    ExtendedNewsDto getNewsById(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0", required = false, value = "page") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(defaultValue = "10", required = false, value = "size") Integer size,
            @Parameter(description = "Фильтр комментариев")
            @RequestBody(required = false) @Valid CommentFilter newsFilter
    );

    @Operation(summary = "Создать новую новость")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешно создана новость"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания новости",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping
    @ResponseStatus(CREATED)
    CreatedNewsDto createNews(
            @Parameter(description = "Данные для создания новости")
            @RequestBody @Valid CreateNewsDto createNewsDto
    );

    @Operation(summary = "Удалить новость по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешно удалена новость"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/{newsId}")
    @ResponseStatus(NO_CONTENT)
    void deleteNewsById(@Parameter(description = "ID новости") @PathVariable Long newsId);

    @Operation(summary = "Полное обновление новости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно обновлена новость"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления новости",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping("/{newsId}")
    UpdatedNewsDto fullUpdate(
            @Parameter(description = "ID новости") @PathVariable Long newsId,
            @Parameter(description = "Данные для полного обновления новости")
            @RequestBody @Validated(FullUpdateNewsMarker.class) UpdateNewsDto updatedNewsDto
    );

    @Operation(summary = "Частичное обновление новости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно частично обновлена новость"),
            @ApiResponse(responseCode = "404", description = "Новость не найдена",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для частичного обновления новости",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{newsId}")
    UpdatedNewsDto partUpdateNewsDto(
            @Parameter(description = "ID новости") @PathVariable Long newsId,
            @Parameter(description = "Данные для частичного обновления новости")
            @RequestBody UpdateNewsDto updatedNewsDto
    );
}
