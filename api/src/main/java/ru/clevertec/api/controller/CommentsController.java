package ru.clevertec.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.api.controller.doc.CommentsControllerDoc;
import ru.clevertec.api.dto.comment.CreateCommentDto;
import ru.clevertec.api.dto.comment.CreatedCommentDto;
import ru.clevertec.api.dto.comment.UpdateCommentDto;
import ru.clevertec.api.dto.comment.UpdatedCommentDto;
import ru.clevertec.api.service.CommentService;
import ru.clevertec.loggingstarter.annotation.LogRequestResponse;

@RestController
@RequestMapping("/api/v1/news/{newsId}/comments")
@LogRequestResponse
@RequiredArgsConstructor
public class CommentsController implements CommentsControllerDoc {
    private final CommentService commentService;

    @Override
    public CreatedCommentDto createComment(Long newsId, CreateCommentDto createCommentDto) {
        return commentService.createComment(newsId, createCommentDto);
    }

    @Override
    public UpdatedCommentDto partUpdateComment(Long commentsId, UpdateCommentDto updateCommentDto) {
        return commentService.partCommentUpdate(commentsId, updateCommentDto);
    }

    @Override
    public void deleteComment(Long newsId, Long commentsId) {
        commentService.deleteComment(newsId, commentsId);
    }
}
