package com.example.Openai;


import java.util.List;
import java.util.Map;

class ChatRequest {
    private List<Map<String, String>> messages;

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }
}


