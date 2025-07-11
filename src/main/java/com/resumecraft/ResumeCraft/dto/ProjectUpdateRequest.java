package com.resumecraft.ResumeCraft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequest {
    private Long id; // Project ID
    private String projectName;
    private String projectDescription;
}
