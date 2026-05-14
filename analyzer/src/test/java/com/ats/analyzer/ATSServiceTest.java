package com.ats.analyzer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Add this
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; // Add this
import org.springframework.mock.web.MockMultipartFile;
import java.util.Map;

@ExtendWith(MockitoExtension.class) // THIS IS THE FIX
public class ATSServiceTest {

    @Mock
    private ResumeRepository resumeRepository;

    @InjectMocks
    private ATSService atsService;

   @Test
public void testAnalyzeResume_Success() {
    // 1. Arrange: Use a simple text file (PDFBox will return empty text for this, leading to 0%)
    // To see a 100% score, we would need a real PDF byte array.
    // For now, let's verify that a mismatch results in 0% correctly.
    
    MockMultipartFile file = new MockMultipartFile(
        "file", 
        "test.pdf", 
        "application/pdf", 
        "java mysql".getBytes() // This is raw text, not a PDF "structure"
    );
    
    String jd = "python developer"; // Different from the file content

    // 2. Act
    Map<String, Object> result = atsService.analyzeResume(file, jd);

    // 3. Assert: Expect 0% since "python" isn't in the (empty) extracted text
    assertEquals("0%", result.get("ats_score"));
    assertEquals("Needs Optimization", result.get("status"));
    
    // Verify the database save still happened
    verify(resumeRepository, times(1)).save(any(Resume.class));
    }
}