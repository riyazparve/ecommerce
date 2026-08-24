package com.riyaz.ecom.jpademo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InstagramPostDto {
    private UUID pageId;
    private String content;
}
