package com.benia.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.benia.backend.dto.ChatRequest;
import com.benia.backend.model.ChatMessage;
import com.benia.backend.repository.ChatMessageRepository;

@Service
public class ChatService {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatResponse chat(ChatRequest request, String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject("http://localhost:8000/chat", request, String.class);

        //salvar a mensagem e a resposta no banco de dados
        ChatMessage chatMessage = new ChatMessage(sessionId, request.getMessage(), response, LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        return new ChatResponse(response, sessionId);
    }

    public List<ChatMessage> getHistory(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            return List.of();
        }

        return chatMessageRepository.findBySessionId(sessionId);
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
