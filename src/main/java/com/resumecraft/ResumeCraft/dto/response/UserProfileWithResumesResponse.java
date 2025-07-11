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
public class UserProfileWithResumesResponse {
    private UserProfileResponse userProfile;
    private List<ResumeResponse> resumes;
}

