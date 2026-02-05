package com.example.interviewer.model;

public record QuestionItem(
    String id,
    InterviewMode mode,
    ExperienceLevel experience,
    String question,
    int rank
) {}
