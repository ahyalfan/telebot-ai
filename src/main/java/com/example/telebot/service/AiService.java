package com.example.telebot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient
                .defaultSystem("You are a very intelligent problem helper")
                .build();
    }

    public String getContent(String content){
        return chatClient.prompt()
                .user(content)
                .call()
                .content();
    }
}
