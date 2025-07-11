package com.resumecraft.ResumeCraft.controller;

import com.resumecraft.ResumeCraft.dto.UpdateProfileRequest;
import com.resumecraft.ResumeCraft.dto.response.ResumeResponse;
import com.resumecraft.ResumeCraft.dto.response.UserProfileWithResumesResponse;
import com.resumecraft.ResumeCraft.exception.UserException;
import com.resumecraft.ResumeCraft.model.Resume;
import com.resumecraft.ResumeCraft.model.User;
import com.resumecraft.ResumeCraft.services.ResumeService;
import com.resumecraft.ResumeCraft.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
 @Autowired
 private UserService userService;
    @Autowired
    private ResumeService resumeService;


    // Endpoint to find user profile by JWT token
    @GetMapping("/profile")
    public ResponseEntity<UserProfileWithResumesResponse> findUserProfileWithResumesByJwt(
            @RequestHeader("Authorization") String jwt) throws UserException {
        UserProfileWithResumesResponse response = userService.findUserProfileWithResumesByJwt(jwt);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String jwt,
                                                 @RequestBody UpdateProfileRequest updateRequest) {
        try {
            // Step 1: Update user details using the service
            User updatedUser = userService.updateUserProfile(jwt, updateRequest);

            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating user and resumes: " + e.getMessage());
        }
    }




    @PostMapping("/resume")
    public ResponseEntity<?> createResume(@RequestBody Resume resume, @RequestHeader("Authorization") String jwt) {
        try {
            // Extract the user profile using the JWT
            UserProfileWithResumesResponse user = userService.findUserProfileWithResumesByJwt(jwt);

            // Call the service method to create the resume and associate it with the user
            Resume createdResume = resumeService.createResume(resume, user);

            return new ResponseEntity<>(createdResume, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating resume: " + e.getMessage());
        }
    }

    @GetMapping("/userProfileWithResumes")
    public ResponseEntity<?> getUserProfileWithResumes(@RequestHeader("Authorization") String jwt) {
        try {
            UserProfileWithResumesResponse response = userService.getLatestUserProfileWithResumes(jwt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error fetching user profile with resumes: " + e.getMessage());
        }
    }




}

