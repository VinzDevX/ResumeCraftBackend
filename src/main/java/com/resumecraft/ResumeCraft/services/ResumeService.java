package com.resumecraft.ResumeCraft.services;

import com.resumecraft.ResumeCraft.dto.ProjectUpdateRequest;
import com.resumecraft.ResumeCraft.dto.response.UserProfileWithResumesResponse;
import com.resumecraft.ResumeCraft.exception.UserException;
import com.resumecraft.ResumeCraft.model.Project;
import com.resumecraft.ResumeCraft.model.Resume;
import com.resumecraft.ResumeCraft.model.User;
import com.resumecraft.ResumeCraft.repository.ProjectRepository;
import com.resumecraft.ResumeCraft.repository.ResumeRepository;
import com.resumecraft.ResumeCraft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    public Resume createResume(Resume resume, UserProfileWithResumesResponse userResponse) throws UserException {
        // Retrieve the user based on the user ID in UserProfileWithResumesResponse
        Optional<User> userOptional = userRepository.findById(userResponse.getUserProfile().getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Associate the resume with the user
            resume.setUser(user);

            // Set the fields of the resume
            resume.setTemplateName(resume.getTemplateName());
            resume.setPersonalInfo(resume.getPersonalInfo());
            resume.setEducationDetails(resume.getEducationDetails());
            resume.setWorkExperience(resume.getWorkExperience());
            resume.setSkills(resume.getSkills());  // Save the list of skills
            resume.setMobileNumber(resume.getMobileNumber());
            resume.setEmail(resume.getEmail());
            resume.setGithubLink(resume.getGithubLink());
            resume.setLinkedinLink(resume.getLinkedinLink());
            resume.setTwitterLink(resume.getTwitterLink());

            // Associate each project with the resume
            List<Project> projects = resume.getProjects();
            if (projects != null && !projects.isEmpty()) {
                for (Project project : projects) {
                    project.setResume(resume);  // Set the resume for each project
                }
            }

            // Save the resume to the repository, which also saves the projects due to CascadeType.ALL
            return resumeRepository.save(resume);
        } else {
            throw new UserException("User not found");
        }
    }



    public Optional<Resume> findResumeById(Long id, User user) {
        // Fetch resume based on ID and ensure it belongs to the user
        return resumeRepository.findByIdAndUserId(id, user.getId());
    }

}
