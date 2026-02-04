package com.example.interviewer.service;

import com.example.interviewer.config.OpenAiProperties;
import com.example.interviewer.model.ChatMessage;
import com.example.interviewer.model.ChatRequest;
import com.example.interviewer.model.ChatResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiChatService {
  private final WebClient webClient;
  private final OpenAiProperties properties;
  private final InterviewPromptBuilder promptBuilder;

  public OpenAiChatService(WebClient webClient, OpenAiProperties properties, InterviewPromptBuilder promptBuilder) {
    this.webClient = webClient;
    this.properties = properties;
    this.promptBuilder = promptBuilder;
  }

  public ChatResponse chat(ChatRequest request) {
    List<Map<String, String>> messages = new ArrayList<>();
    messages.add(Map.of("role", "system", "content", promptBuilder.systemPrompt(request.mode(), request.experience())));

    if (request.history() != null) {
      for (ChatMessage message : request.history()) {
        messages.add(Map.of("role", message.role(), "content", message.content()));
      }
    }

    messages.add(Map.of("role", "user", "content", request.prompt()));

    Map<String, Object> payload = Map.of(
        "model", properties.chatModel(),
        "messages", messages,
        "temperature", 0.4
    );

    OpenAiChatResponse response = webClient.post()
        .uri("/v1/chat/completions")
        .bodyValue(payload)
        .retrieve()
        .bodyToMono(OpenAiChatResponse.class)
        .block();

    if (response == null || response.choices().isEmpty()) {
      return new ChatResponse("No response from OpenAI. Please try again.");
    }

    return new ChatResponse(response.choices().get(0).message().content());
  }

  public record OpenAiChatResponse(List<Choice> choices) {
    public record Choice(Message message) {}
    public record Message(String content) {}
  }
}
