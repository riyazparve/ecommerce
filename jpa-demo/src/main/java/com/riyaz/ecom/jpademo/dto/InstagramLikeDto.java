package com.riyaz.ecom.jpademo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InstagramLikeDto {
    private UUID id;
    private UUID postId;
    private UUID userId;
}
