package com.resumecraft.ResumeCraft.services;

import com.resumecraft.ResumeCraft.config.JwtProvider;
import com.resumecraft.ResumeCraft.dto.UpdateProfileRequest;
import com.resumecraft.ResumeCraft.dto.response.ProjectResponse;
import com.resumecraft.ResumeCraft.dto.response.ResumeResponse;
import com.resumecraft.ResumeCraft.dto.response.UserProfileResponse;
import com.resumecraft.ResumeCraft.dto.response.UserProfileWithResumesResponse;
import com.resumecraft.ResumeCraft.exception.UserException;
import com.resumecraft.ResumeCraft.model.Resume;
import com.resumecraft.ResumeCraft.model.User;
import com.resumecraft.ResumeCraft.repository.ResumeRepository;
import com.resumecraft.ResumeCraft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserProfileWithResumesResponse findUserProfileWithResumesByJwt(String jwt) throws UserException {
        String userId = jwtProvider.getIdFromToken(jwt);

        // Fetch user details
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Map User entity to UserProfileResponse with id
        UserProfileResponse userProfileResponse = new UserProfileResponse(
                user.getId(), // Set the user's ID
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getMobileNumber(),
                user.getRole()
        );

        // Fetch resumes for the user
        List<Resume> resumes = resumeRepository.findByUserId(Long.valueOf(userId));

        // Map Resume entities to ResumeResponse DTOs with ids
        List<ResumeResponse> resumeResponses = resumes.stream().map(resume -> new ResumeResponse(
                resume.getId(), // Set the resume's ID
                resume.getMobileNumber(),
                resume.getTemplateName(),
                resume.getPersonalInfo(),
                resume.getEducationDetails(),
                resume.getWorkExperience(),
                resume.getSkills(),
                resume.getGithubLink(),
                resume.getLinkedinLink(),
                resume.getTwitterLink(),
                // Map the Project entities to ProjectResponse DTOs
                resume.getProjects().stream()
                        .map(project -> new ProjectResponse(
                                project.getId(),
                                project.getProjectName(),
                                project.getProjectDescription()
                        ))
                        .collect(Collectors.toList()) // Collect as List<ProjectResponse>
        )).collect(Collectors.toList());


        // Return the combined response
        return new UserProfileWithResumesResponse(userProfileResponse, resumeResponses);
    }


    public User updateUserProfile(String jwt, UpdateProfileRequest updateProfileRequest) {
        // Step 1: Extract user ID from JWT
        String userId = jwtProvider.getIdFromToken(jwt);

        System.out.println("user id is...."+userId);

        // Step 2: Find user by ID
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Step 3: Update user details
        user.setFirstName(updateProfileRequest.getFirstName());
        user.setLastName(updateProfileRequest.getLastName());
        user.setEmail(updateProfileRequest.getEmail());
        user.setMobileNumber(updateProfileRequest.getMobileNumber());

        // Encode the password if it was changed
        if (updateProfileRequest.getPassword() != null && !updateProfileRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateProfileRequest.getPassword()));
        }
        user.setRole("USER");
        // Step 4: Save the updated user
        return userRepository.save(user);
    }
    public User updateAdminProfile(String jwt, UpdateProfileRequest updateProfileRequest) {
        // Step 1: Extract user ID from JWT
        String userId = jwtProvider.getIdFromToken(jwt);

        System.out.println("user id is...."+userId);

        // Step 2: Find user by ID
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Step 3: Update user details
        user.setFirstName(updateProfileRequest.getFirstName());
        user.setLastName(updateProfileRequest.getLastName());
        user.setEmail(updateProfileRequest.getEmail());
        user.setMobileNumber(updateProfileRequest.getMobileNumber());

        // Encode the password if it was changed
        if (updateProfileRequest.getPassword() != null && !updateProfileRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateProfileRequest.getPassword()));
        }
        user.setRole("ADMIN");
        // Step 4: Save the updated user
        return userRepository.save(user);
    }


//    public ResumeResponse findUserResumesByJwt(String jwt) {
//        // Extract user ID from JWT
//        String userId = jwtProvider.getIdFromToken(jwt);
//
//        // Fetch user details
//        User user = userRepository.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        // Fetch resumes for the user
//        List<Resume> resumes = resumeRepository.findByUserId(Long.valueOf(userId));
//
//        // Ensure the resumes list is not empty
//        if (resumes.isEmpty()) {
//            throw new RuntimeException("No resumes found for user with ID: " + userId);
//        }
//
//        // Sort the resumes by creation time or ID (latest resume comes first)
//        resumes.sort((r1, r2) -> r2.getId().compareTo(r1.getId()));  // Assuming higher ID means more recent
//
//        // Get the latest resume (first after sorting)
//        Resume latestResume = resumes.get(0);
//
//        // Map the latest resume to ResumeResponse DTO
//        ResumeResponse latestResumeResponse = new ResumeResponse(
//                latestResume.getId(),
//                latestResume.getMobileNumber(),
//                latestResume.getTemplateName(),
//                latestResume.getPersonalInfo(),
//                latestResume.getEducationDetails(),
//                latestResume.getWorkExperience(),
//                latestResume.getSkills(),
//                latestResume.getGithubLink(),
//                latestResume.getLinkedinLink(),
//                latestResume.getTwitterLink(),
//                latestResume.getProjects().stream()
//                        .map(project -> new ProjectResponse(
//                                project.getId(),
//                                project.getProjectName(),
//                                project.getProjectDescription()
//                        ))
//                        .collect(Collectors.toList()) // Collect as List<ProjectResponse>
//        );
//
//        // Return the latest resume response
//        return latestResumeResponse;
//    }


    public UserProfileWithResumesResponse getLatestUserProfileWithResumes(String jwt) {
        // Extract user ID from JWT
        String userId = jwtProvider.getIdFromToken(jwt);

        // Fetch user details
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Create UserProfileResponse
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setFirstName(user.getFirstName());
        userProfileResponse.setLastName(user.getLastName());
        userProfileResponse.setMobileNumber(user.getMobileNumber());
        userProfileResponse.setEmail(user.getEmail());


        // Fetch resumes for the user
        List<Resume> resumes = resumeRepository.findByUserId(Long.valueOf(userId));

        // Map resumes to ResumeResponse DTOs
        List<ResumeResponse> resumeResponses = resumes.stream().map(resume -> new ResumeResponse(
                resume.getId(),
                resume.getMobileNumber(),
                resume.getTemplateName(),
                resume.getPersonalInfo(),
                resume.getEducationDetails(),
                resume.getWorkExperience(),
                resume.getSkills(),
                resume.getGithubLink(),
                resume.getLinkedinLink(),
                resume.getTwitterLink(),
                resume.getProjects().stream()
                        .map(project -> new ProjectResponse(
                                project.getId(),
                                project.getProjectName(),
                                project.getProjectDescription()
                        ))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());

        // Create and return the combined response
        return new UserProfileWithResumesResponse(userProfileResponse, resumeResponses);
    }
}
