package com.project.text_share.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextResponse {
    private String title;
    private String content;
}