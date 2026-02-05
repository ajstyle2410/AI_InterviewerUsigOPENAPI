# AI Interviewer (OpenAI + Spring Boot)

AI interview practice tool with role-play sessions, pronunciation coaching, grammar correction, chat, evaluation, and speech support.

## Features
- Technical / HR / Manager interview modes
- Fresher and experienced flows
- Role-play starter with interviewer role + company context
- Pronunciation guide (IPA, syllables, tips, practice lines)
- Pronunciation practice injection into chat
- Grammar correction endpoint for quick answer cleanup
- Answer evaluation with score + improvement suggestions
- Top-ranked question search
- Mic input and TTS playback
- Session controls: scenario, goal, tone, timer, question-only mode
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

Open: http://localhost:8080

## API Examples

### Chat
```bash
curl -X POST http://localhost:8080/api/chat \
  -H 'Content-Type: application/json' \
  -d '{"prompt":"Tell me about yourself.","mode":"HR","experience":"FRESHER","history":[]}'
```

### Role-play starter
```bash
curl -X POST http://localhost:8080/api/coach/roleplay/start \
  -H 'Content-Type: application/json' \
  -d '{"mode":"TECHNICAL","experience":"EXPERIENCED","interviewerRole":"Engineering Manager"}'
```

### Pronunciation guide
```bash
curl -X POST http://localhost:8080/api/coach/pronunciation \
  -H 'Content-Type: application/json' \
  -d '{"text":"stakeholder communication"}'
```

### Grammar correction
```bash
curl -X POST http://localhost:8080/api/coach/grammar \
  -H 'Content-Type: application/json' \
  -d '{"answer":"I has leaded a project"}'
```
