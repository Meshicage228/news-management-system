package ru.clevertec.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.service.CommentService;
import ru.clevertec.loggingstarter.annotation.LogRequestResponse;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/news/{newsId}/comments")
@LogRequestResponse
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CreatedCommentDto createComment(@PathVariable("newsId") Long newsId,
                                           @RequestBody @Valid CreateCommentDto createCommentDto) {
        return commentService.createComment(newsId, createCommentDto);
    }

    @PatchMapping("/{commentsId}")
    public UpdatedCommentDto partUpdateComment(@PathVariable("commentsId") Long commentsId,
                                               @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.partCommentUpdate(commentsId, updateCommentDto);
    }

    @DeleteMapping("/{commentsId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(@PathVariable("newsId") Long newsId,
                              @PathVariable("commentsId") Long commentsId) {
        commentService.deleteComment(newsId, commentsId);
    }
}
