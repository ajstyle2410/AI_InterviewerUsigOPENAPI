package com.example.interviewer.service;

import com.example.interviewer.config.OpenAiProperties;
import com.example.interviewer.model.EvaluateRequest;
import com.example.interviewer.model.EvaluateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiEvaluationService {
  private final WebClient webClient;
  private final OpenAiProperties properties;
  private final ObjectMapper objectMapper;

  public OpenAiEvaluationService(WebClient webClient, OpenAiProperties properties, ObjectMapper objectMapper) {
    this.webClient = webClient;
    this.properties = properties;
    this.objectMapper = objectMapper;
  }

  public EvaluateResponse evaluate(EvaluateRequest request) {
    String system = "You are an interview evaluator. Respond in JSON with keys: "
        + "scorePercent (0-100 integer), grammarIssues (array of strings), improvements (array), "
        + "correctedAnswer (string). Keep it concise.";

    String user = String.format("Question: %s\nAnswer: %s\nMode: %s\nExperience: %s",
        request.question(), request.answer(), request.mode(), request.experience());

    Map<String, Object> payload = Map.of(
        "model", properties.chatModel(),
        "messages", List.of(
            Map.of("role", "system", "content", system),
            Map.of("role", "user", "content", user)
        ),
        "response_format", Map.of("type", "json_object"),
        "temperature", 0.2
    );

    OpenAiResponse response = webClient.post()
        .uri("/v1/chat/completions")
        .bodyValue(payload)
        .retrieve()
        .bodyToMono(OpenAiResponse.class)
        .block();

    if (response == null || response.choices().isEmpty()) {
      return new EvaluateResponse(0, List.of("No response from OpenAI."), List.of(), "");
    }

    String content = response.choices().get(0).message().content();
    try {
      return objectMapper.readValue(content, EvaluateResponse.class);
    } catch (JsonProcessingException ex) {
      return new EvaluateResponse(0, List.of("Failed to parse evaluation."), List.of(), "");
    }
  }

  public record OpenAiResponse(List<Choice> choices) {
    public record Choice(Message message) {}
    public record Message(String content) {}
  }
}
