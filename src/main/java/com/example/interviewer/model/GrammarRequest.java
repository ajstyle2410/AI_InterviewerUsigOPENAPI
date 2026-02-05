package com.example.interviewer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GrammarRequest(
    @NotBlank @Size(max = 2000) String answer
) {}
