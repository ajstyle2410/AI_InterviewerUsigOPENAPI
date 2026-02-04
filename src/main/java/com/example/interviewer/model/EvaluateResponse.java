package com.example.interviewer.model;

import java.util.List;

public record EvaluateResponse(
    int scorePercent,
    List<String> grammarIssues,
    List<String> improvements,
    String correctedAnswer
) {}
