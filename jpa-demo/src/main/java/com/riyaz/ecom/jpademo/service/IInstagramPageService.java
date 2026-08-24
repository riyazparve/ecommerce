package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramPage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInstagramPageService {
    List<InstagramPage> getAllPages();
    Optional<InstagramPage> getPageById(UUID id);
    List<InstagramPage> getPagesByCreatorId(UUID creatorId);
    InstagramPage createPage(InstagramPage page);
    InstagramPage updatePage(UUID id, InstagramPage page);
    void deletePage(UUID id);
}
