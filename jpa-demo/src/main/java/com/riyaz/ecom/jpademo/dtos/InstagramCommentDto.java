package com.riyaz.ecom.jpademo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class InstagramCommentDto {
    private UUID postId;
    private UUID userId;
    private String text;
}
