package com.example.interviewer.controller;

import com.example.interviewer.model.ChatRequest;
import com.example.interviewer.model.ChatResponse;
import com.example.interviewer.service.InputRestrictionService;
import com.example.interviewer.service.OpenAiChatService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
  private final OpenAiChatService chatService;
  private final InputRestrictionService restrictionService;

  public ChatController(OpenAiChatService chatService, InputRestrictionService restrictionService) {
    this.chatService = chatService;
    this.restrictionService = restrictionService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
    restrictionService.validateChat(request);
    return chatService.chat(request);
  }
}
