package com.riyaz.ecom.jpademo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class InstagramPostDto {
    private UUID pageId;
    private String content;
}
