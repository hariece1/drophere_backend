package com.project.text_share.DTO;

import lombok.Data;

@Data
public class TextCreateRequest {
    private String title;
    private String content;
    private boolean isEncrypted;
    private String password;  // nullable
    private boolean isViewable;
    private String contentType;
    private Long userId;      // link to user
    private String expiresAt; // ISO format or use LocalDateTime
}

