package com.resumecraft.ResumeCraft.services;

import com.resumecraft.ResumeCraft.model.ResumeTemplate;
import com.resumecraft.ResumeCraft.repository.ResumeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeTemplateService {
    @Autowired
    private ResumeTemplateRepository resumeTemplateRepository;

    public ResumeTemplate saveTemplate(ResumeTemplate template) {
        return resumeTemplateRepository.save(template);
    }

    public List<ResumeTemplate> getAllTemplates() {
        return resumeTemplateRepository.findAll();
    }

    public Optional<ResumeTemplate> getTemplateByName(String templateName) {
        return resumeTemplateRepository.findByTemplateName(templateName);
    }


    public Optional<ResumeTemplate> getTemplateById(Long id) {
        return resumeTemplateRepository.findById(id);
    }

    public void deleteTemplate(Long id) {
        resumeTemplateRepository.deleteById(id);
    }
}
