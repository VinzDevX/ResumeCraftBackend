package com.resumecraft.ResumeCraft.repository;

import com.resumecraft.ResumeCraft.model.ResumeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeTemplateRepository extends JpaRepository<ResumeTemplate, Long> {
    Optional<ResumeTemplate> findByTemplateName(String templateName);
}
