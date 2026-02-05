package com.example.interviewer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PronunciationRequest(
    @NotBlank @Size(max = 300) String text
) {}
