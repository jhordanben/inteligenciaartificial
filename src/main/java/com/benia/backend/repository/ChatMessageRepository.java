package com.benia.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.benia.backend.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository <ChatMessage, Long> {

    List<ChatMessage> findBySessionId(String sessionId);
    
}
