package com.project.text_share.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.text_share.DTO.TextCreateRequest;
import com.project.text_share.DTO.TextResponse;
import com.project.text_share.DTO.TextResponseALL;
import com.project.text_share.Entity.Text;
import com.project.text_share.Service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> createText(@RequestBody TextCreateRequest request) throws JsonProcessingException {
        System.out.println("Incoming JSON: " + new ObjectMapper().writeValueAsString(request));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
//        System.out.println(request);
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

    @GetMapping("/download/txt/{slug}")
    public ResponseEntity<byte[]> downloadTextAsTxt(
            @PathVariable String slug,
            @RequestParam(required = false) String password) {

        Text text = textService.getTextBySlug(slug, password);
        String filename = text.getTitle().replaceAll("[^a-zA-Z0-9]", "_") + ".txt";
        byte[] content = text.getContent().getBytes();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename)
                .header("Content-Type", "text/plain")
                .body(content);
    }
    @GetMapping("/getuserslung")
    public ResponseEntity<List<TextResponseALL>> getMyTexts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("JJJJJ");
        List<TextResponseALL> texts = textService.getTextsByUsername(username);
        return ResponseEntity.ok(texts);
    }
    @DeleteMapping("/delete/{slug}")
    public ResponseEntity<?> deleteText(@PathVariable String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        textService.deleteTextBySlug(slug, username);
        return ResponseEntity.ok(Map.of("message", "Text deleted successfully!"));
    }


}
