package com.project.text_share.Service;


import com.project.text_share.DTO.TextCreateRequest;
import com.project.text_share.Entity.Text;

public interface TextService {
    String createText(TextCreateRequest request, String username);
    Text getTextBySlug(String slug,String password);

}
