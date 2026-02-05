package com.example.interviewer.service;

import com.example.interviewer.model.ExperienceLevel;
import com.example.interviewer.model.InterviewMode;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class InterviewPromptBuilder {
  public String systemPrompt(
      InterviewMode mode,
      ExperienceLevel experience,
      boolean questionOnly,
      String scenario,
      String goal,
      String tone
  ) {
    String modeInstruction = switch (mode) {
      case TECHNICAL -> "Focus on core CS, programming, and system design questions.";
      case HR -> "Focus on behavioral, culture-fit, and communication questions.";
      case MANAGER -> "Focus on leadership, ownership, and stakeholder management questions.";
    };

    String experienceInstruction = switch (experience) {
      case FRESHER -> "Target entry-level candidates with beginner-friendly guidance.";
      case EXPERIENCED -> "Target experienced candidates with depth and real-world scenarios.";
    };

    String questionOnlyInstruction = questionOnly
        ? "Ask only a single interview question. Do not provide explanations or feedback."
        : "Ask one question at a time and provide actionable feedback.";

    String scenarioInstruction = (scenario == null || scenario.isBlank())
        ? ""
        : String.format(Locale.ROOT, "Scenario: %s.", scenario);
    String goalInstruction = (goal == null || goal.isBlank())
        ? ""
        : String.format(Locale.ROOT, "Goal: %s.", goal);
    String toneInstruction = (tone == null || tone.isBlank())
        ? ""
        : String.format(Locale.ROOT, "Tone: %s.", tone);

    return String.format(Locale.ROOT,
        "You are an AI interview coach. %s %s %s %s %s %s",
        modeInstruction,
        experienceInstruction,
        questionOnlyInstruction,
        scenarioInstruction,
        goalInstruction,
        toneInstruction
    ).trim();
  }
}
