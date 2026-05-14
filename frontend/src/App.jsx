import React, { useState } from 'react';
import axios from 'axios';
import { Upload, CheckCircle, AlertCircle, FileText, History } from 'lucide-react';

function App() {
  const [file, setFile] = useState(null);
  const [jd, setJd] = useState('');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleAnalyze = async () => {
    if (!file || !jd) return alert("Please upload a resume and paste a Job Description.");
    
    setLoading(true);
    const formData = new FormData();
    formData.append("file", file);
    formData.append("jd", jd);

    try {
      // Connecting to your Spring Boot API
      const response = await axios.post("http://localhost:8080/api/ats/analyze", formData);
      setResult(response.data);
    } catch (error) {
      console.error("Error analyzing resume:", error);
      alert("Connection failed. Check if Spring Boot is running on port 8080.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 p-4 md:p-8 font-sans text-slate-900">
      <div className="max-w-5xl mx-auto">
        <header className="mb-10 text-center">
          <h1 className="text-4xl font-extrabold text-indigo-700">ATS Resume Pro</h1>
          <p className="text-slate-500 mt-2">AI-powered gap analysis for your tech career.</p>
        </header>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Input Section */}
          <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 h-fit">
            <h2 className="text-xl font-bold mb-6 flex items-center gap-2">
              <Upload size={20} className="text-indigo-600"/> Analysis Input
            </h2>
            
            <div className="space-y-6">
              <div>
                <label className="block text-sm font-semibold mb-2">Upload Resume (PDF/Docx)</label>
                <div className="border-2 border-dashed border-slate-200 rounded-xl p-4 hover:border-indigo-400 transition cursor-pointer relative">
                  <input 
                    type="file" 
                    onChange={(e) => setFile(e.target.files[0])}
                    className="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
                  />
                  <div className="flex flex-col items-center text-slate-400">
                    <FileText size={32} className="mb-2"/>
                    <span className="text-sm">{file ? file.name : "Select your resume file"}</span>
                  </div>
                </div>
              </div>

              <div>
                <label className="block text-sm font-semibold mb-2">Target Job Description</label>
                <textarea 
                  rows="8"
                  value={jd}
                  onChange={(e) => setJd(e.target.value)}
                  placeholder="Paste the full job description here..."
                  className="w-full p-3 border border-slate-200 rounded-xl focus:ring-2 focus:ring-indigo-500 outline-none transition resize-none text-sm"
                />
              </div>

              <button 
                onClick={handleAnalyze}
                disabled={loading}
                className="w-full py-4 bg-indigo-600 text-white rounded-xl font-bold hover:bg-indigo-700 disabled:bg-slate-300 transition flex items-center justify-center gap-2"
              >
                {loading ? "Analyzing Skills..." : "Run AI Analysis"}
              </button>
            </div>
          </div>

          {/* Result Section */}
          <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 min-h-[500px]">
             <h2 className="text-xl font-bold mb-6 flex items-center gap-2">
              <CheckCircle size={20} className="text-indigo-600"/> Analysis Report
            </h2>
            
            {result ? (
              <div className="space-y-8 animate-in fade-in zoom-in duration-300">
                <div className="flex items-center justify-between p-6 bg-indigo-50 rounded-2xl border border-indigo-100">
                   <div>
                    <p className="text-xs font-bold text-indigo-400 uppercase tracking-widest">Match Score</p>
                    <p className="text-5xl font-black text-indigo-700">{result.ats_score}</p>
                   </div>
                   <div className="text-right">
                    <p className="text-xs font-bold text-indigo-400 uppercase tracking-widest">Status</p>
                    <p className={`font-bold ${result.status === 'Ready for Application' ? 'text-green-600' : 'text-amber-600'}`}>
                      {result.status}
                    </p>
                   </div>
                </div>

                <div>
                  <h3 className="text-sm font-bold text-slate-500 mb-3 uppercase flex items-center gap-2">
                    Found Skills & Phrases
                  </h3>
                  <div className="flex flex-wrap gap-2">
                    {result.matching_phrases.map((p, i) => (
                      <span key={i} className="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-semibold">
                        {p}
                      </span>
                    ))}
                  </div>
                </div>

                {result.career_advice.length > 0 && (
                  <div className="p-4 bg-amber-50 rounded-xl border border-amber-200">
                    <h3 className="text-sm font-bold text-amber-800 mb-3 flex items-center gap-2">
                      <AlertCircle size={16}/> Strategic Advice
                    </h3>
                    <ul className="text-xs text-amber-800 space-y-2 list-disc ml-4 leading-relaxed">
                      {result.career_advice.map((tip, i) => <li key={i}>{tip}</li>)}
                    </ul>
                  </div>
                )}
              </div>
            ) : (
              <div className="h-full flex flex-col items-center justify-center text-slate-400 text-center opacity-40 py-20">
                <History size={64} className="mb-4"/>
                <p>Submit your resume to see the <br/>AI-generated match report here.</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;