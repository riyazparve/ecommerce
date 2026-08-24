package com.riyaz.ecom.jpademo.controller;

import com.riyaz.ecom.jpademo.dto.InstagramLikeDto;
import com.riyaz.ecom.jpademo.exception.ValidationException;
import com.riyaz.ecom.jpademo.mapper.InstagramLikeMapper;
import com.riyaz.ecom.jpademo.models.InstagramLike;
import com.riyaz.ecom.jpademo.service.IInstagramLikeService;
import com.riyaz.ecom.jpademo.validator.InstagramLikeValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/likes")
public class InstagramLikeController {

    private final IInstagramLikeService likeService;
    private final InstagramLikeMapper likeMapper;
    private final InstagramLikeValidator likeValidator;

    public InstagramLikeController(IInstagramLikeService likeService,
                                  InstagramLikeMapper likeMapper,
                                  InstagramLikeValidator likeValidator) {
        this.likeService = likeService;
        this.likeMapper = likeMapper;
        this.likeValidator = likeValidator;
    }

    @GetMapping
    public List<InstagramLike> getAllLikes() {
        return likeService.getAllLikes();
    }

    @GetMapping("/{id}")
    public InstagramLike getLikeById(@PathVariable UUID id) {
        return likeService.getLikeById(id)
                .orElseThrow(() -> new RuntimeException("Like not found"));
    }

    @GetMapping("/post/{postId}")
    public List<InstagramLike> getLikesByPostId(@PathVariable UUID postId) {
        return likeService.getLikesByPostId(postId);
    }

    @GetMapping("/user/{userId}")
    public List<InstagramLike> getLikesByUserId(@PathVariable UUID userId) {
        return likeService.getLikesByUserId(userId);
    }

    @PostMapping
    public InstagramLike createLike(@RequestBody InstagramLikeDto likeDto) {
        List<String> errors = likeValidator.validate(likeDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramLike like = likeMapper.toEntity(likeDto);
        return likeService.createLike(like);
    }

    @PutMapping("/{id}")
    public InstagramLike updateLike(@PathVariable UUID id, @RequestBody InstagramLikeDto likeDto) {
        List<String> errors = likeValidator.validate(likeDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramLike like = likeMapper.toEntity(likeDto);
        return likeService.updateLike(id, like);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable UUID id) {
        likeService.deleteLike(id);
    }
}
