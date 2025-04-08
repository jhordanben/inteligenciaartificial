package com.benia.backend.controller;

import com.benia.backend.model.ChatMessage;
import com.benia.backend.dto.ChatRequest;
import com.benia.backend.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody ChatRequest request, @RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject("http://localhost:8000/chat", request, String.class);

        // salvar a mensagem e a resposta no banco de dados
        ChatMessage chatMessage = new ChatMessage(sessionId, request.getMessage(), response, LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        return ResponseEntity.ok(new ChatResponse(response, sessionId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(chatMessageRepository.findBySessionId(sessionId));
    }
}


class ChatResponse {
    private String response;
    private String sessionId;

    public ChatResponse(String response, String sessionId) {
        this.response = response;
        this.sessionId = sessionId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
