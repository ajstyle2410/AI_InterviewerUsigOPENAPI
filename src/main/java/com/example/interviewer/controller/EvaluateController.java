package com.example.interviewer.controller;

import com.example.interviewer.model.EvaluateRequest;
import com.example.interviewer.model.EvaluateResponse;
import com.example.interviewer.service.OpenAiEvaluationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evaluate")
public class EvaluateController {
  private final OpenAiEvaluationService evaluationService;

  public EvaluateController(OpenAiEvaluationService evaluationService) {
    this.evaluationService = evaluationService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public EvaluateResponse evaluate(@Valid @RequestBody EvaluateRequest request) {
    return evaluationService.evaluate(request);
  }
}
