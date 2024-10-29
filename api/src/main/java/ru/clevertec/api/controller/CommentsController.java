package ru.clevertec.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.core.dto.comment.CreateCommentDto;
import ru.clevertec.core.dto.comment.CreatedCommentDto;
import ru.clevertec.core.dto.comment.UpdateCommentDto;
import ru.clevertec.core.dto.comment.UpdatedCommentDto;
import ru.clevertec.core.service.CommentService;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/news/{newsId}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CreatedCommentDto createComment(@PathVariable Long newsId,
                                           @RequestBody @Valid CreateCommentDto createCommentDto) {
        return commentService.createComment(newsId, createCommentDto);
    }

    @PatchMapping("/{commentsId}")
    public UpdatedCommentDto partUpdateComment(@PathVariable Long commentsId,
                                               @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.partCommentUpdate(commentsId, updateCommentDto);
    }

    @DeleteMapping("/{commentsId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(@PathVariable Long commentsId) {
        commentService.deleteComment(commentsId);
    }
}
