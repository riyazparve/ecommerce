package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramPage;
import com.riyaz.ecom.jpademo.repository.InstagramPageRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstagramPageService implements IInstagramPageService {

    private final InstagramPageRepo pageRepo;

    public InstagramPageService(InstagramPageRepo pageRepo) {
        this.pageRepo = pageRepo;
    }

    @Override
    public List<InstagramPage> getAllPages() {
        return pageRepo.findAll();
    }

    @Override
    public Optional<InstagramPage> getPageById(UUID id) {
        return pageRepo.findById(id);
    }

    @Override
    public List<InstagramPage> getPagesByCreatorId(UUID creatorId) {
        return pageRepo.findByCreatorId(creatorId);
    }

    @Override
    public InstagramPage createPage(InstagramPage page) {
        return pageRepo.save(page);
    }

    @Override
    public InstagramPage updatePage(UUID id, InstagramPage page) {
        return pageRepo.findById(id).map(existing -> {
            existing.setCreator(page.getCreator());
            return pageRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Page not found"));
    }

    @Override
    public void deletePage(UUID id) {
        pageRepo.deleteById(id);
    }
}
