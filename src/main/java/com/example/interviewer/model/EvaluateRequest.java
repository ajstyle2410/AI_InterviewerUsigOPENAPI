package com.example.interviewer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EvaluateRequest(
    @NotBlank @Size(max = 800) String question,
    @NotBlank @Size(max = 2000) String answer,
    @NotNull InterviewMode mode,
    @NotNull ExperienceLevel experience
) {}
