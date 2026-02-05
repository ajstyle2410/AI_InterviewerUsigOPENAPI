package com.example.interviewer.model;

import java.util.List;

public record PronunciationResponse(
    String ipa,
    List<String> syllableBreakdown,
    List<String> practiceTips,
    List<String> practiceSentences
) {}
