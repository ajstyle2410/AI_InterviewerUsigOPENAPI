package com.example.interviewer.service;

import com.example.interviewer.config.OpenAiProperties;
import com.example.interviewer.model.GrammarRequest;
import com.example.interviewer.model.GrammarResponse;
import com.example.interviewer.model.PronunciationRequest;
import com.example.interviewer.model.PronunciationResponse;
import com.example.interviewer.model.RolePlayRequest;
import com.example.interviewer.model.RolePlayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiCoachingService {
  private final WebClient webClient;
  private final OpenAiProperties properties;
  private final ObjectMapper objectMapper;

  public OpenAiCoachingService(WebClient webClient, OpenAiProperties properties, ObjectMapper objectMapper) {
    this.webClient = webClient;
    this.properties = properties;
    this.objectMapper = objectMapper;
  }

  public PronunciationResponse pronunciationGuide(PronunciationRequest request) {
    String system = "You are an English pronunciation coach. Return strict JSON: "
        + "ipa (string), syllableBreakdown (array), practiceTips (array), practiceSentences (array).";
    String user = "Create a pronunciation guide for: " + request.text();
    String json = chatJson(system, user);

    try {
      return objectMapper.readValue(json, PronunciationResponse.class);
    } catch (JsonProcessingException ex) {
      return new PronunciationResponse("", List.of(), List.of("Could not parse guide."), List.of());
    }
  }

  public GrammarResponse grammarCorrection(GrammarRequest request) {
    String system = "You are a grammar coach for interview answers. Return strict JSON: "
        + "corrected (string), issues (array), quickFixes (array).";
    String user = "Correct and improve this answer: " + request.answer();
    String json = chatJson(system, user);

    try {
      return objectMapper.readValue(json, GrammarResponse.class);
    } catch (JsonProcessingException ex) {
      return new GrammarResponse(request.answer(), List.of("Could not parse grammar response."), List.of());
    }
  }

  public RolePlayResponse rolePlayStart(RolePlayRequest request) {
    String system = "You are a realistic interviewer. Return strict JSON with keys opening and expectedFocus.";
    String user = String.format(
        "Mode: %s, Experience: %s, InterviewerRole: %s, CompanyContext: %s, CandidateGoal: %s",
        request.mode(),
        request.experience(),
        request.interviewerRole(),
        request.companyContext(),
        request.candidateGoal()
    );
    String json = chatJson(system, user);

    try {
      return objectMapper.readValue(json, RolePlayResponse.class);
    } catch (JsonProcessingException ex) {
      return new RolePlayResponse("Let's begin. Tell me about yourself.", "communication and relevance");
    }
  }

  private String chatJson(String system, String user) {
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
      return "{}";
    }

    return response.choices().get(0).message().content();
  }

  public record OpenAiResponse(List<Choice> choices) {
    public record Choice(Message message) {}
    public record Message(String content) {}
  }
}
