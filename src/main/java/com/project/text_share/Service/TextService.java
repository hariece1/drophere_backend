package com.project.text_share.Service;


import com.project.text_share.DTO.TextCreateRequest;
import com.project.text_share.DTO.TextResponseALL;
import com.project.text_share.Entity.Text;
import com.project.text_share.Entity.User;

import java.util.List;

public interface TextService {
    String createText(TextCreateRequest request, String username);
    Text getTextBySlug(String slug,String password);
    List<TextResponseALL> getTextsByUsername(String username);

}
