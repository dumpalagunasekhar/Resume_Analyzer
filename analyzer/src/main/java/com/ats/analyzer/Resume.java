package com.ats.analyzer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resumes") // This will be the table name in MySQL
@Data 
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    
    private String score;

    @Column(columnDefinition = "TEXT") // Allows storing long lists of keywords
    private String missingKeywords;
}