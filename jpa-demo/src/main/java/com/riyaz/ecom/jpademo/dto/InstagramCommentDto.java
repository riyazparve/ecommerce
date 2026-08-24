package com.riyaz.ecom.jpademo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InstagramCommentDto {
    private UUID postId;
    private UUID userId;
    private String text;
}
