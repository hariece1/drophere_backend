package com.project.text_share.Controller;

import com.project.text_share.Service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/text")
public class TextController {
    @Autowired
    private TextService textService;

    @GetMapping("/check")
    public String register() {
        return "Hi from here text";
    }
}
