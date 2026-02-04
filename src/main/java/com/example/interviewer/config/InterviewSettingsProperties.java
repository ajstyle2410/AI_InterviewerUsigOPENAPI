package com.example.interviewer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "interview")
public record InterviewSettingsProperties(
    int maxPromptLength,
    int maxAnswerLength,
    int maxHistory
) {}
