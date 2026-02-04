package com.example.interviewer.controller;

import com.example.interviewer.model.ExperienceLevel;
import com.example.interviewer.model.InterviewMode;
import com.example.interviewer.model.QuestionItem;
import com.example.interviewer.service.QuestionBankService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
  private final QuestionBankService questionBankService;

  public QuestionController(QuestionBankService questionBankService) {
    this.questionBankService = questionBankService;
  }

  @GetMapping
  public List<QuestionItem> search(
      @RequestParam(value = "mode", required = false) InterviewMode mode,
      @RequestParam(value = "experience", required = false) ExperienceLevel experience,
      @RequestParam(value = "q", required = false) String query,
      @RequestParam(value = "limit", defaultValue = "5") int limit
  ) {
    int safeLimit = Math.min(Math.max(limit, 1), 10);
    return questionBankService.search(mode, experience, query, safeLimit);
  }
}
