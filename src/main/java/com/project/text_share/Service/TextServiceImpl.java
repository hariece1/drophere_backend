package com.project.text_share.Service;

import com.project.text_share.DTO.TextCreateRequest;
import com.project.text_share.Entity.Text;
import com.project.text_share.Entity.User;
import com.project.text_share.Repo.TextRepository;
import com.project.text_share.Repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TextServiceImpl implements TextService{

    private final TextRepository textRepository;
    private final UserRepository userRepository;

    public TextServiceImpl(TextRepository textRepository, UserRepository userRepository) {
        this.textRepository = textRepository;
        this.userRepository = userRepository;
    }


    @Override
    public String createText(TextCreateRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Text text = new Text();
        text.setTitle(request.getTitle());
        text.setContent(request.getContent());
        text.setEncrypted(request.isEncrypted());
        text.setPassword(request.getPassword());
        text.setViewable(request.isViewable());
        text.setContentType(request.getContentType());
        text.setUser(user);
        text.setCreatedAt(LocalDateTime.now());

        if (request.getExpiresAt() != null) {
            text.setExpiresAt(LocalDateTime.parse(request.getExpiresAt()));
        }

        String slug = generateSlug(username, request.getTitle());
        text.setSlug(slug);

        textRepository.save(text);
        return slug;
    }


    private String generateSlug(String username, String title) {
        String cleanTitle = title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        return username.toLowerCase() + "-" + cleanTitle;
    }

    public Text getTextBySlug(String slug) {
        Text text = textRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Text not found"));

        // Check expiration
        if (text.getExpiresAt() != null && text.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "This text has expired.");
        }

        return text;
    }





}
