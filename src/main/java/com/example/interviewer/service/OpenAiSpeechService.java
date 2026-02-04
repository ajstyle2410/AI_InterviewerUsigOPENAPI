package com.example.interviewer.service;

import com.example.interviewer.config.OpenAiProperties;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiSpeechService {
  private final WebClient webClient;
  private final OpenAiProperties properties;

  public OpenAiSpeechService(WebClient webClient, OpenAiProperties properties) {
    this.webClient = webClient;
    this.properties = properties;
  }

  public String transcribe(byte[] audioBytes, String filename) {
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("file", new NamedByteArrayResource(audioBytes, filename))
        .contentType(MediaType.APPLICATION_OCTET_STREAM);
    builder.part("model", properties.transcriptionModel());

    Map<String, Object> response = webClient.post()
        .uri("/v1/audio/transcriptions")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .retrieve()
        .bodyToMono(Map.class)
        .block();

    if (response == null || !response.containsKey("text")) {
      return "";
    }

    return String.valueOf(response.get("text"));
  }

  public byte[] speak(String text) {
    Map<String, Object> payload = Map.of(
        "model", properties.speechModel(),
        "voice", properties.defaultVoice(),
        "input", text
    );

    return webClient.post()
        .uri("/v1/audio/speech")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_OCTET_STREAM)
        .bodyValue(payload)
        .retrieve()
        .bodyToMono(byte[].class)
        .block();
  }

  private static final class NamedByteArrayResource extends ByteArrayResource {
    private final String filename;

    private NamedByteArrayResource(byte[] byteArray, String filename) {
      super(byteArray);
      this.filename = filename == null ? "audio.wav" : filename;
    }

    @Override
    public String getFilename() {
      return filename;
    }
  }
}
