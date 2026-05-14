package com.ats.analyzer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import com.ats.analyzer.Resume;
import com.ats.analyzer.ResumeRepository;
import com.ats.analyzer.ATSService;
@RestController
@RequestMapping("/api/ats")
@CrossOrigin(origins = "*") 
public class ATSController {

    private final ATSService atsService;
    private final ResumeRepository resumeRepository;

    // Constructor Injection for both Service and Repository
    public ATSController(ATSService atsService, ResumeRepository resumeRepository) {
        this.atsService = atsService;
        this.resumeRepository = resumeRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<List<Resume>> getHistory() {
        // This fetches all records from your MySQL 'resumes' table
        return ResponseEntity.ok(resumeRepository.findAll());
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jd") String jd) {
        
        if (file.isEmpty() || jd.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(atsService.analyzeResume(file, jd));
    }
}