package com.project.text_share.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TextResponseALL {
    private String title;
    private String content;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    // Constructor, Getters, Setters
    public TextResponseALL(String title, String content, String slug, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}

