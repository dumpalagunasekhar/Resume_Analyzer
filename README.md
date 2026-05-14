# 📄 Resume Analyzer (ATS-Optimized)

A powerful full-stack web application designed to bridge the gap between job seekers and Applicant Tracking Systems (ATS). This tool analyzes resumes, calculates match scores against job descriptions, and identifies strategic keyword gaps to help candidates land more interviews.

---

## 🚀 Key Features
*   **Resume Parsing:** Automatically extracts and cleans text from uploaded documents.
*   **ATS Scoring:** Provides a real-time percentage match based on job requirements.
*   **Keyword Intelligence:** Identifies missing technical skills and industry-specific keywords.
*   **Full-Stack Architecture:** Decoupled React frontend and Spring Boot backend for high performance.
*   **Responsive Dashboard:** A clean, intuitive UI for a seamless user experience.

---

## 🛠️ Tech Stack

**Frontend:**
*   **React.js** (Vite)
*   **Tailwind CSS / Custom CSS**
*   **Axios** for API integration

**Backend:**
*   **Java 8+**
*   **Spring Boot** (REST API)
*   **Maven** for dependency management

---

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone [https://github.com/dumpalagunasekhar/Resume_Analyzer.git](https://github.com/dumpalagunasekhar/Resume_Analyzer.git)
cd Resume_Analyzer
```
### 2. Backend Setup (Spring Boot)
*  cd analyzer
*  mvn clean install
*  mvn spring-boot:run

---

### 3. Frontend Setup (React)
*  cd frontend
*  npm install
*  npm run dev

---


### 📊 How It Works
Input: The user pastes a Job Description and uploads a Resume.

Processing: The Spring Boot backend uses string manipulation and NLP logic to compare the two datasets.

Output: The React frontend displays a visual breakdown of the match score and provides a list of "Missing Keywords" to add for better ranking.

---

### 📌 Future Enhancements
*  [ ] Integration with LLMs for AI-generated resume summaries.

*  [ ] Support for .docx and image formats.

*  [ ] History tracking to save and compare previous analysis results.

---

### 👤 Author
Guna Sekhar

Full-Stack Developer
