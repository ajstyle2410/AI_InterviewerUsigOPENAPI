package com.example.interviewer.service;

import com.example.interviewer.config.InterviewSettingsProperties;
import com.example.interviewer.model.ChatRequest;
import com.example.interviewer.model.EvaluateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InputRestrictionService {
  private final InterviewSettingsProperties settings;

  public InputRestrictionService(InterviewSettingsProperties settings) {
    this.settings = settings;
  }

  public void validateChat(ChatRequest request) {
    if (request.prompt().length() > settings.maxPromptLength()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prompt exceeds limit");
    }

    if (request.history() != null && request.history().size() > settings.maxHistory()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "History exceeds limit");
    }
  }

  public void validateEvaluate(EvaluateRequest request) {
    if (request.question().length() > settings.maxPromptLength()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question exceeds limit");
    }

    if (request.answer().length() > settings.maxAnswerLength()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer exceeds limit");
    }
  }
}
