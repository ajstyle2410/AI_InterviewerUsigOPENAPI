package com.example.interviewer.controller;

import com.example.interviewer.model.GrammarRequest;
import com.example.interviewer.model.GrammarResponse;
import com.example.interviewer.model.PronunciationRequest;
import com.example.interviewer.model.PronunciationResponse;
import com.example.interviewer.model.RolePlayRequest;
import com.example.interviewer.model.RolePlayResponse;
import com.example.interviewer.service.OpenAiCoachingService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coach")
public class CoachController {
  private final OpenAiCoachingService coachingService;

  public CoachController(OpenAiCoachingService coachingService) {
    this.coachingService = coachingService;
  }

  @PostMapping(path = "/pronunciation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PronunciationResponse pronunciation(@Valid @RequestBody PronunciationRequest request) {
    return coachingService.pronunciationGuide(request);
  }

  @PostMapping(path = "/grammar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public GrammarResponse grammar(@Valid @RequestBody GrammarRequest request) {
    return coachingService.grammarCorrection(request);
  }

  @PostMapping(path = "/roleplay/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public RolePlayResponse rolePlayStart(@Valid @RequestBody RolePlayRequest request) {
    return coachingService.rolePlayStart(request);
  }
}
