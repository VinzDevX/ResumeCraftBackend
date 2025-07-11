package com.resumecraft.ResumeCraft.repository;

import com.resumecraft.ResumeCraft.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
