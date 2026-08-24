package com.riyaz.ecom.jpademo.controller;

import com.riyaz.ecom.jpademo.dto.InstagramCommentDto;
import com.riyaz.ecom.jpademo.exception.ValidationException;
import com.riyaz.ecom.jpademo.mapper.InstagramCommentMapper;
import com.riyaz.ecom.jpademo.models.InstagramComment;
import com.riyaz.ecom.jpademo.service.IInstagramCommentService;
import com.riyaz.ecom.jpademo.validator.InstagramCommentValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class InstagramCommentController {

    private final IInstagramCommentService commentService;
    private final InstagramCommentMapper commentMapper;
    private final InstagramCommentValidator commentValidator;

    public InstagramCommentController(IInstagramCommentService commentService,
                                     InstagramCommentMapper commentMapper,
                                     InstagramCommentValidator commentValidator) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.commentValidator = commentValidator;
    }

    @GetMapping
    public List<InstagramComment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public InstagramComment getCommentById(@PathVariable UUID id) {
        return commentService.getCommentById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @GetMapping("/post/{postId}")
    public List<InstagramComment> getCommentsByPostId(@PathVariable UUID postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user/{userId}")
    public List<InstagramComment> getCommentsByUserId(@PathVariable UUID userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @PostMapping
    public InstagramComment createComment(@RequestBody InstagramCommentDto commentDto) {
        List<String> errors = commentValidator.validate(commentDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramComment comment = commentMapper.toEntity(commentDto);
        return commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    public InstagramComment updateComment(@PathVariable UUID id, @RequestBody InstagramCommentDto commentDto) {
        List<String> errors = commentValidator.validate(commentDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramComment comment = commentMapper.toEntity(commentDto);
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
    }
}
