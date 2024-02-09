package com.kaleidofin.kaleidobot.controller;

import com.kaleidofin.kaleidobot.dto.HuggingFaceResponse;
import com.kaleidofin.kaleidobot.dto.Pages;
import com.kaleidofin.kaleidobot.dto.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kaleidofin.kaleidobot.service.ChatbotService;

@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @GetMapping("/prompts")
    public ResponseEntity<Pages> getPrompts() {
        return chatbotService.getPrompts();
    }

    @PostMapping("/reply")
    public ResponseEntity<HuggingFaceResponse> reply(@RequestParam("block") String block, @RequestBody Question question) {
        return chatbotService.reply(block, question.getQuestion());
    }

}
