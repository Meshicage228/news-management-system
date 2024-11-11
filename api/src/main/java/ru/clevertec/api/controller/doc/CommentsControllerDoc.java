package ru.clevertec.api.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.globalexceptionhandlingstarter.dto.ExceptionResponse;

public interface CommentsControllerDoc {
    @Operation(summary = "Создать новый комментарий к новости")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешно создан комментарий"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания комментария",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreatedCommentDto createComment(
            @Parameter(description = "ID новости", required = true, example = "1") @PathVariable Long newsId,
            @Parameter(description = "Данные для создания комментария", required = true)
            @RequestBody @Valid CreateCommentDto createCommentDto
    );

    @Operation(summary = "Частичное обновление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно обновлен комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления комментария",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{commentsId}")
    UpdatedCommentDto partUpdateComment(
            @Parameter(description = "ID комментария", required = true, example = "1") @PathVariable Long commentsId,
            @Parameter(description = "Данные для обновления комментария", required = true)
            @RequestBody UpdateCommentDto updateCommentDto
    );

    @Operation(summary = "Удалить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешно удален комментарий"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/{commentsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(
            @Parameter(description = "ID новости", required = true, example = "1") @PathVariable Long newsId,
            @Parameter(description = "ID комментария", required = true, example = "1") @PathVariable Long commentsId
    );
}
