package com.example.interviewer.model;

import java.util.List;

public record GrammarResponse(
    String corrected,
    List<String> issues,
    List<String> quickFixes
) {}
