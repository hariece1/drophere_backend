package com.project.text_share.Controller;

import com.project.text_share.DTO.TextCreateRequest;
import com.project.text_share.DTO.TextResponse;
import com.project.text_share.Entity.Text;
import com.project.text_share.Service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/text")
public class TextController {
    @Autowired
    private TextService textService;

    @GetMapping("/check")
    public String check(){
        return "OK";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createText(@RequestBody TextCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Hi");
        String slug = textService.createText(request, username);
        return ResponseEntity.ok(Map.of("slug", slug, "message", "Text shared successfully!"));
    }
    @GetMapping("/get/{slug}")
    public ResponseEntity<TextResponse> getTextBySlug(
            @PathVariable String slug,
            @RequestParam(required = false) String password) {

        Text text = textService.getTextBySlug(slug, password);
        TextResponse response = new TextResponse(text.getTitle(), text.getContent());
        return ResponseEntity.ok(response);
    }

}
