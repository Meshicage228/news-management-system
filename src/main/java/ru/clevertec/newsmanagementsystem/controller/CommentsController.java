package ru.clevertec.newsmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsmanagementsystem.dto.comment.CreateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.CreatedCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdateCommentDto;
import ru.clevertec.newsmanagementsystem.dto.comment.UpdatedCommentDto;
import ru.clevertec.newsmanagementsystem.service.CommentService;

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
                                               @PathVariable Long newsId,
                                               @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.partCommentUpdate(newsId, commentsId, updateCommentDto);
    }

    @DeleteMapping("/{commentsId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(@PathVariable Long newsId,
                              @PathVariable Long commentsId) {
        commentService.deleteComment(newsId, commentsId);
    }
}
