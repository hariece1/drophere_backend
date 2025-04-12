package com.project.text_share.Service;

import com.project.text_share.DTO.TextCreateRequest;
import com.project.text_share.DTO.TextResponseALL;
import com.project.text_share.Entity.Text;
import com.project.text_share.Entity.User;
import com.project.text_share.Repo.TextRepository;
import com.project.text_share.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TextServiceImpl implements TextService{

    private final PasswordEncoder passwordEncoder;
    private final TextRepository textRepository;
    private final UserRepository userRepository;

    public TextServiceImpl(PasswordEncoder passwordEncoder, TextRepository textRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.textRepository = textRepository;
        this.userRepository = userRepository;
    }


    @Override
    public String createText(TextCreateRequest request, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println(request);
            Text text = new Text();
            text.setTitle(request.getTitle());
            text.setContent(request.getContent());
            text.setEncrypted(request.isEncrypted());
            text.setViewable(request.isViewable());
            text.setContentType(request.getContentType());
            text.setUser(user);
            text.setCreatedAt(LocalDateTime.now());
            System.out.println("Bye");
            // 🔐 Encrypt password only if enabled and password is provided
            if (request.isEncrypted() && request.getPassword() != null && !request.getPassword().isEmpty()) {
                try {
                    text.setPassword(passwordEncoder.encode(request.getPassword()));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to encrypt password");
                }
            } else {
                text.setPassword(null); // Not encrypted or password not provided
            }

            // 🕒 Expiration logic
            try {
                if (request.getExpiresAt() != null && !request.getExpiresAt().isBlank()) {
                    text.setExpiresAt(LocalDateTime.parse(request.getExpiresAt()));
                } else {
                    text.setExpiresAt(LocalDateTime.now().plusDays(1)); // default expiry: 1 day
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid expiration date format");
            }

            // 🌐 Slug generation
            String slug = generateSlug(username, request.getTitle());

            text.setSlug(slug);

            textRepository.save(text);
            return slug;

        } catch (RuntimeException e) {
            // You can customize this error handling (e.g. log the error, send response status, etc.)
            throw new RuntimeException("Failed to create text: " + e.getMessage());
        }
    }




    private String generateSlug(String username, String title) {
        String cleanTitle = title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        return username.toLowerCase() + "-" + cleanTitle;
    }

    @Override
    public Text getTextBySlug(String slug, String password) {
        Text text = textRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Text not found"));

        // Check expiration
        if (text.getExpiresAt() != null && text.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Text has expired");
        }

        // If encrypted, check password
        if (text.isEncrypted()) {
            if (password == null || password.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password required");
            }
            if (!passwordEncoder.matches(password, text.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
            }
        }

        textRepository.save(text);

        return text;
    }

    public List<TextResponseALL> getTextsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Text> texts = textRepository.findByUser(user);

        return texts.stream()
                .map(t -> new TextResponseALL(t.getTitle(), t.getContent(), t.getSlug(), t.getCreatedAt(), t.getExpiresAt()))
                .collect(Collectors.toList());
    }





}
