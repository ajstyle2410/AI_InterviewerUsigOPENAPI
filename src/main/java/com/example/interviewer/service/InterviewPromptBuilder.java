package com.example.interviewer.service;

import com.example.interviewer.model.ExperienceLevel;
import com.example.interviewer.model.InterviewMode;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class InterviewPromptBuilder {
  public String systemPrompt(InterviewMode mode, ExperienceLevel experience) {
    String modeInstruction = switch (mode) {
      case TECHNICAL -> "Focus on core CS, programming, and system design questions.";
      case HR -> "Focus on behavioral, culture-fit, and communication questions.";
      case MANAGER -> "Focus on leadership, ownership, and stakeholder management questions.";
    };

    String experienceInstruction = switch (experience) {
      case FRESHER -> "Target entry-level candidates with beginner-friendly guidance.";
      case EXPERIENCED -> "Target experienced candidates with depth and real-world scenarios.";
    };

    return String.format(Locale.ROOT,
        "You are an AI interview coach. %s %s Ask one question at a time and provide actionable feedback.",
        modeInstruction,
        experienceInstruction
    );
  }
}
