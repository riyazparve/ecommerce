package com.riyaz.ecom.jpademo.controller;

import com.riyaz.ecom.jpademo.dto.InstagramPostDto;
import com.riyaz.ecom.jpademo.exception.ValidationException;
import com.riyaz.ecom.jpademo.mapper.InstagramPostMapper;
import com.riyaz.ecom.jpademo.models.InstagramPost;
import com.riyaz.ecom.jpademo.service.IInstagramPostService;
import com.riyaz.ecom.jpademo.validator.InstagramPostValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class InstagramPostController {

    private final IInstagramPostService postService;
    private final InstagramPostMapper postMapper;
    private final InstagramPostValidator postValidator;

    public InstagramPostController(IInstagramPostService postService,
                                  InstagramPostMapper postMapper,
                                  InstagramPostValidator postValidator) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.postValidator = postValidator;
    }

    @GetMapping
    public List<InstagramPost> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public InstagramPost getPostById(@PathVariable UUID id) {
        return postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @GetMapping("/page/{pageId}")
    public List<InstagramPost> getPostsByPageId(@PathVariable UUID pageId) {
        return postService.getPostsByPageId(pageId);
    }

    @PostMapping
    public InstagramPost createPost(@RequestBody InstagramPostDto postDto) {
        List<String> errors = postValidator.validate(postDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramPost post = postMapper.toEntity(postDto);
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public InstagramPost updatePost(@PathVariable UUID id, @RequestBody InstagramPostDto postDto) {
        List<String> errors = postValidator.validate(postDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramPost post = postMapper.toEntity(postDto);
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
    }
}
