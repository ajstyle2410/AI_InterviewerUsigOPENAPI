package com.example.interviewer.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(OpenAiProperties.class)
public class OpenAiConfig {
  @Bean
  public WebClient openAiWebClient(OpenAiProperties properties) {
    ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .build();

    return WebClient.builder()
        .baseUrl(properties.baseUrl())
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.apiKey())
        .exchangeStrategies(strategies)
        .build();
  }
}
