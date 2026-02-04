# AI Interviewer (OpenAI + Spring Boot)

This project provides a ChatGPT-style interview coach for technical, HR, and manager rounds. It supports freshers and experienced candidates, offers a chat interface, and includes speech-to-text and text-to-speech hooks with the OpenAI API.

## Features
- Interview mode selection (Technical/HR/Manager)
- Fresher vs experienced interview tailoring
- Chat history context for follow-up questions
- Speech transcription endpoint (OpenAI audio transcribe)
- Text-to-speech endpoint (OpenAI audio speech)
- Simple web UI with mic input (browser speech recognition)

## Requirements
- Java 17+
- Maven 3.8+
- OpenAI API key

## Configuration
Set your API key as an environment variable:

```bash
export OPENAI_API_KEY=your_key_here
```

## Run
```bash
mvn spring-boot:run
```

Visit: `http://localhost:8080`

## API Examples

### Chat
```bash
curl -X POST http://localhost:8080/api/chat \
  -H 'Content-Type: application/json' \
  -d '{
    "prompt": "Tell me about yourself.",
    "mode": "HR",
    "experience": "FRESHER",
    "history": []
  }'
```

### Transcribe
```bash
curl -X POST http://localhost:8080/api/speech/transcribe \
  -F "file=@sample.wav"
```

### Speak
```bash
curl -X POST http://localhost:8080/api/speech/speak \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'text=Hello'
```
