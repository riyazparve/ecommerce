package com.riyaz.ecom.jpademo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class InstagramLikeDto {
    private UUID id;
    private UUID postId;
    private UUID userId;
}
