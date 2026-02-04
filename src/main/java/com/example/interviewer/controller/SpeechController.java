package com.example.interviewer.controller;

import com.example.interviewer.service.OpenAiSpeechService;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/speech")
public class SpeechController {
  private final OpenAiSpeechService speechService;

  public SpeechController(OpenAiSpeechService speechService) {
    this.speechService = speechService;
  }

  @PostMapping(path = "/transcribe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> transcribe(@RequestParam("file") MultipartFile file) throws IOException {
    String transcript = speechService.transcribe(file.getBytes(), file.getOriginalFilename());
    return ResponseEntity.ok(transcript);
  }

  @PostMapping(path = "/speak", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<byte[]> speak(@RequestParam("text") String text) {
    byte[] audio = speechService.speak(text);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reply.mp3")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(audio);
  }
}
