package com.riyaz.ecom.jpademo.controller;

import com.riyaz.ecom.jpademo.dto.InstagramPageDto;
import com.riyaz.ecom.jpademo.exception.ValidationException;
import com.riyaz.ecom.jpademo.mapper.InstagramPageMapper;
import com.riyaz.ecom.jpademo.models.InstagramPage;
import com.riyaz.ecom.jpademo.service.IInstagramPageService;
import com.riyaz.ecom.jpademo.validator.InstagramPageValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pages")
public class InstagramPageController {

    private final IInstagramPageService pageService;
    private final InstagramPageMapper pageMapper;
    private final InstagramPageValidator pageValidator;

    public InstagramPageController(IInstagramPageService pageService,
                                  InstagramPageMapper pageMapper,
                                  InstagramPageValidator pageValidator) {
        this.pageService = pageService;
        this.pageMapper = pageMapper;
        this.pageValidator = pageValidator;
    }

    @GetMapping
    public List<InstagramPage> getAllPages() {
        return pageService.getAllPages();
    }

    @GetMapping("/{id}")
    public InstagramPage getPageById(@PathVariable UUID id) {
        return pageService.getPageById(id)
                .orElseThrow(() -> new RuntimeException("Page not found"));
    }

    @GetMapping("/creator/{creatorId}")
    public List<InstagramPage> getPagesByCreatorId(@PathVariable UUID creatorId) {
        return pageService.getPagesByCreatorId(creatorId);
    }

    @PostMapping
    public InstagramPage createPage(@RequestBody InstagramPageDto pageDto) {
        List<String> errors = pageValidator.validate(pageDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramPage page = pageMapper.toEntity(pageDto);
        return pageService.createPage(page);
    }

    @PutMapping("/{id}")
    public InstagramPage updatePage(@PathVariable UUID id, @RequestBody InstagramPageDto pageDto) {
        List<String> errors = pageValidator.validate(pageDto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        InstagramPage page = pageMapper.toEntity(pageDto);
        return pageService.updatePage(id, page);
    }

    @DeleteMapping("/{id}")
    public void deletePage(@PathVariable UUID id) {
        pageService.deletePage(id);
    }
}
