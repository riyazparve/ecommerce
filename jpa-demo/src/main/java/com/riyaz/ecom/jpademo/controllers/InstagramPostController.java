package com.riyaz.ecom.jpademo.controllers;

import com.riyaz.ecom.jpademo.dtos.InstagramPostDto;
import com.riyaz.ecom.jpademo.exceptions.ValidationException;
import com.riyaz.ecom.jpademo.mapper.InstagramPostMapper;
import com.riyaz.ecom.jpademo.models.InstagramPost;
import com.riyaz.ecom.jpademo.services.IInstagramPostService;
import com.riyaz.ecom.jpademo.validators.InstagramPostValidator;
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
