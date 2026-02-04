package com.example.interviewer.service;

import com.example.interviewer.model.ExperienceLevel;
import com.example.interviewer.model.InterviewMode;
import com.example.interviewer.model.QuestionItem;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankService {
  private static final List<QuestionItem> QUESTIONS = List.of(
      new QuestionItem("tech-1", InterviewMode.TECHNICAL, ExperienceLevel.FRESHER,
          "Explain the difference between a stack and a queue with examples.", 95),
      new QuestionItem("tech-2", InterviewMode.TECHNICAL, ExperienceLevel.EXPERIENCED,
          "Design a scalable URL shortener and discuss trade-offs.", 96),
      new QuestionItem("tech-3", InterviewMode.TECHNICAL, ExperienceLevel.EXPERIENCED,
          "How would you optimize a slow SQL query in production?", 92),
      new QuestionItem("tech-4", InterviewMode.TECHNICAL, ExperienceLevel.FRESHER,
          "What is REST, and how is it different from SOAP?", 91),
      new QuestionItem("tech-5", InterviewMode.TECHNICAL, ExperienceLevel.EXPERIENCED,
          "Describe how you would design a rate limiter for an API.", 93),
      new QuestionItem("hr-1", InterviewMode.HR, ExperienceLevel.FRESHER,
          "Tell me about yourself and why you chose this field.", 97),
      new QuestionItem("hr-2", InterviewMode.HR, ExperienceLevel.EXPERIENCED,
          "Describe a time you handled conflict in a team.", 94),
      new QuestionItem("hr-3", InterviewMode.HR, ExperienceLevel.FRESHER,
          "How do you handle feedback from a manager?", 90),
      new QuestionItem("manager-1", InterviewMode.MANAGER, ExperienceLevel.EXPERIENCED,
          "How do you prioritize projects when resources are limited?", 93),
      new QuestionItem("manager-2", InterviewMode.MANAGER, ExperienceLevel.FRESHER,
          "How would you communicate bad news to stakeholders?", 90)
  );

  public List<QuestionItem> search(InterviewMode mode, ExperienceLevel experience, String query, int limit) {
    Stream<QuestionItem> stream = QUESTIONS.stream();

    if (mode != null) {
      stream = stream.filter(item -> item.mode() == mode);
    }

    if (experience != null) {
      stream = stream.filter(item -> item.experience() == experience);
    }

    if (query != null && !query.isBlank()) {
      String lowered = query.toLowerCase(Locale.ROOT);
      stream = stream.filter(item -> item.question().toLowerCase(Locale.ROOT).contains(lowered));
    }

    return stream
        .sorted(Comparator.comparingInt(QuestionItem::rank).reversed())
        .limit(limit)
        .toList();
  }
}
