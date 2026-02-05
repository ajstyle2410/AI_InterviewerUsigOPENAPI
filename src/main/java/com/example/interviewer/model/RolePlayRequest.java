package com.example.interviewer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RolePlayRequest(
    @NotNull InterviewMode mode,
    @NotNull ExperienceLevel experience,
    @NotBlank @Size(max = 120) String interviewerRole,
    @Size(max = 200) String companyContext,
    @Size(max = 200) String candidateGoal
) {}
