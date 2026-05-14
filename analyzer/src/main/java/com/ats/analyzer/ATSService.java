package com.ats.analyzer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.*;

@Service
public class ATSService {

    private final ResumeRepository resumeRepository;

    public ATSService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Map<String, Object> analyzeResume(MultipartFile file, String jd) {
        // 1. Extract REAL text from the uploaded PDF
        String resumeText = extractTextFromFile(file).toLowerCase();
        String jdText = jd.toLowerCase();

        // 2. Comprehensive Keyword List for Tech Roles
        List<String> techKeywords = Arrays.asList(
            "python", "django", "flask", "java", "spring", "react", "mysql", 
            "postgresql", "docker", "aws", "git", "rest api", "devops", "junit", "javascript", "css"
        );

        // 3. Find matches only if they exist in BOTH the Resume AND the JD
        List<String> foundKeywords = techKeywords.stream()
            .filter(keyword -> jdText.contains(keyword) && resumeText.contains(keyword))
            .toList();

        // 4. Find what the JD wants but the Resume is missing
        List<String> missingKeywords = techKeywords.stream()
            .filter(keyword -> jdText.contains(keyword) && !resumeText.contains(keyword))
            .toList();

        // 5. Dynamic Scoring: (Matches / Total keywords requested in JD)
        long totalKeywordsInJD = techKeywords.stream().filter(jdText::contains).count();
        int score = 0;
        if (totalKeywordsInJD > 0) {
            score = (int) (( (double) foundKeywords.size() / totalKeywordsInJD) * 100);
        }

        // 6. Save to MySQL
        Resume entity = new Resume();
        entity.setFileName(file.getOriginalFilename());
        entity.setScore(score + "%");
        entity.setMissingKeywords(missingKeywords.isEmpty() ? "None" : String.join(", ", missingKeywords));
        resumeRepository.save(entity);

        Map<String, Object> result = new HashMap<>();
        result.put("ats_score", score + "%");
        result.put("status", score > 75 ? "Ready for Application" : "Needs Optimization");
        result.put("matching_phrases", foundKeywords);
        result.put("career_advice", generateAdvice(missingKeywords, score));
        return result;
    }

    private String extractTextFromFile(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(document);
        } catch (Exception e) {
            return ""; // Return empty if PDF is corrupted or not a PDF
        }
    }

    private List<String> generateAdvice(List<String> missing, int score) {
        List<String> advice = new ArrayList<>();
        if (score < 50) advice.add("Critical: This resume is a poor match for this specific role.");
        if (!missing.isEmpty()) advice.add("Add these keywords to your skills section: " + missing);
        advice.add("Quantify your achievements (e.g., 'Improved performance by 20%').");
        return advice;
    }
}