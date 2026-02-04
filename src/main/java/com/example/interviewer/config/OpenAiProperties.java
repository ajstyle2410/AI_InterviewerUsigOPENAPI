package com.example.interviewer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai")
public record OpenAiProperties(
    String apiKey,
    String baseUrl,
    String chatModel,
    String speechModel,
    String transcriptionModel,
    String defaultVoice
) {}
