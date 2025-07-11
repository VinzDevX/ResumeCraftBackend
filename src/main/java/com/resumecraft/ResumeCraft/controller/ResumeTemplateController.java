package com.resumecraft.ResumeCraft.controller;

import com.resumecraft.ResumeCraft.model.ResumeTemplate;
import com.resumecraft.ResumeCraft.services.ResumeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/templates")
public class ResumeTemplateController {
    @Autowired
    private ResumeTemplateService resumeTemplateService;

    @PostMapping("/add")
    public ResponseEntity<ResumeTemplate> addTemplate(@RequestBody ResumeTemplate template) {
        ResumeTemplate savedTemplate = resumeTemplateService.saveTemplate(template);
        return ResponseEntity.ok(savedTemplate);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResumeTemplate>> getAllTemplates() {
        return ResponseEntity.ok(resumeTemplateService.getAllTemplates());
    }

    @GetMapping("/{templateName}")
    public ResponseEntity<ResumeTemplate> getTemplateByName(@PathVariable String templateName) {
        return resumeTemplateService.getTemplateByName(templateName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable Long id) {
        Optional<ResumeTemplate> templateOptional = resumeTemplateService.getTemplateById(id);
        if (templateOptional.isPresent()) {
            resumeTemplateService.deleteTemplate(id);
            return ResponseEntity.ok("Template deleted successfully"); // 200 OK with message
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}

