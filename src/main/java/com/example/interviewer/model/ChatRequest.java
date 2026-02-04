package com.example.interviewer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ChatRequest(
    @NotBlank @Size(max = 1000) String prompt,
    @NotNull InterviewMode mode,
    @NotNull ExperienceLevel experience,
    @Size(max = 20) List<ChatMessage> history,
    Boolean questionOnly
) {}
