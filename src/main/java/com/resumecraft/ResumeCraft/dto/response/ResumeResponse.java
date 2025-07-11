package com.resumecraft.ResumeCraft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeResponse {
    private Long id;
    private String mobileNumber;
    private String templateName;
    private String personalInfo;
    private String educationDetails;
    private String workExperience;
    private List<String> skills; // List of skills// Email ID
    private String githubLink;  // GitHub link
    private String linkedinLink;  // LinkedIn link
    private String twitterLink;
    private List<ProjectResponse> projects;  // List of project responses
}
