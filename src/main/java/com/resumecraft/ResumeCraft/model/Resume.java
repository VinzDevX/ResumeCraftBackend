package com.resumecraft.ResumeCraft.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String templateName;
    private String personalInfo;
    private String educationDetails;
    private String workExperience;

    // Updated field for multiple skills
    @ElementCollection
    @CollectionTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "skill")
    private List<String> skills;  // List of skills

    private String mobileNumber;
    private String email;  // Email ID
    private String githubLink;  // GitHub link
    private String linkedinLink;  // LinkedIn link
    private String twitterLink;  // Twitter link

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // OneToMany relationship with Project
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
}
