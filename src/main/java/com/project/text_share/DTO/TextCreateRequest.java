package com.project.text_share.DTO;

import lombok.Data;

@Data
public class TextCreateRequest {
    private String title;
    private String content;
    private boolean Encrypted;
    private String password;  // nullable
    private boolean Viewable;
    private String contentType;
    private Long userId;      // link to user
    private String expiresAt; // ISO format or use LocalDateTime
    @Override
    public String toString() {
        return "TextCreateRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isEncrypted=" + Encrypted +
                ", password='" + (password != null ? password : "null") + '\'' +
                ", isViewable=" + Viewable +
                ", contentType='" + contentType + '\'' +
                ", userId=" + userId +
                ", expiresAt='" + expiresAt + '\'' +
                '}';
    }
}

