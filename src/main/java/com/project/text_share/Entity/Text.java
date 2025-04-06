package com.project.text_share.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private boolean isViewable;

    @Column(unique = true, nullable = false)
    private String slug;

    private boolean isEncrypted;

    private String password;

    private String userId;

    private String contentType;

    private LocalDateTime lastViewedAt;

}
