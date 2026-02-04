package com.example.interviewer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ChatRequest(
    @NotBlank String prompt,
    @NotNull InterviewMode mode,
    @NotNull ExperienceLevel experience,
    List<ChatMessage> history
) {}
