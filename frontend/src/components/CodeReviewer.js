import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { Upload, Play, Download, FileText, AlertCircle, CheckCircle, Lightbulb, Shield } from 'lucide-react';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { tomorrow } from 'react-syntax-highlighter/dist/esm/styles/prism';

import { CodeReviewService } from '../services/CodeReviewService';
import ReviewCard from './ReviewCard';

const CodeReviewer = ({ onNewReview, recentReviews }) => {
  const [code, setCode] = useState('');
  const [fileName, setFileName] = useState('');
  const [selectedProvider, setSelectedProvider] = useState('OpenAI GPT-4');
  const [availableProviders, setAvailableProviders] = useState([]);
  const [isReviewing, setIsReviewing] = useState(false);
  const [currentReview, setCurrentReview] = useState(null);

  useEffect(() => {
    loadProviders();
  }, []);

  const loadProviders = async () => {
    try {
      const providers = await CodeReviewService.getAvailableProviders();
      setAvailableProviders(providers);
    } catch (error) {
      console.error('Error loading providers:', error);
    }
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file && file.name.endsWith('.java')) {
      const reader = new FileReader();
      reader.onload = (e) => {
        setCode(e.target.result);
        setFileName(file.name);
        toast.success(`File "${file.name}" loaded successfully`);
      };
      reader.readAsText(file);
    } else {
      toast.error('Please select a valid Java file (.java)');
    }
  };

  const handleReview = async () => {
    if (!code.trim()) {
      toast.error('Please enter or upload Java code to review');
      return;
    }

    if (!selectedProvider) {
      toast.error('Please select an AI provider');
      return;
    }

    try {
      setIsReviewing(true);
      const review = await CodeReviewService.reviewCode(code, selectedProvider, fileName);
      
      if (review.success) {
        setCurrentReview(review);
        onNewReview(review);
        toast.success('Code review completed successfully!');
      } else {
        toast.error(review.errorMessage || 'Review failed');
      }
    } catch (error) {
      toast.error(error.message || 'Failed to review code');
    } finally {
      setIsReviewing(false);
    }
  };

  const handleClear = () => {
    setCode('');
    setFileName('');
    setCurrentReview(null);
  };

  const handleExport = () => {
    if (!currentReview) {
      toast.error('No review to export');
      return;
    }

    const report = generateReport(currentReview);
    const blob = new Blob([report], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `code-review-${currentReview.id || 'report'}.txt`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    toast.success('Report exported successfully!');
  };

  const generateReport = (review) => {
    return `
AI-POWERED JAVA CODE REVIEW REPORT
====================================
Generated: ${new Date().toLocaleString()}
AI Provider: ${review.aiProvider}
File: ${review.fileName || 'N/A'}

SUMMARY
-------
${review.summary}

STATISTICS
----------
Total Issues: ${review.totalIssues}
Errors: ${review.errors?.length || 0}
Warnings: ${review.warnings?.length || 0}
Suggestions: ${review.suggestions?.length || 0}
Good Practices: ${review.goodPractices?.length || 0}

${review.errors?.length > 0 ? `
ERRORS
------
${review.errors.map((error, index) => `${index + 1}. ${error}`).join('\n')}
` : ''}

${review.warnings?.length > 0 ? `
WARNINGS
--------
${review.warnings.map((warning, index) => `${index + 1}. ${warning}`).join('\n')}
` : ''}

${review.suggestions?.length > 0 ? `
SUGGESTIONS
-----------
${review.suggestions.map((suggestion, index) => `${index + 1}. ${suggestion}`).join('\n')}
` : ''}

${review.goodPractices?.length > 0 ? `
GOOD PRACTICES
--------------
${review.goodPractices.map((practice, index) => `${index + 1}. ${practice}`).join('\n')}
` : ''}

ORIGINAL CODE
-------------
${code}
    `.trim();
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <h2 className="text-2xl font-bold text-gray-900 mb-2">Java Code Reviewer</h2>
        <p className="text-gray-600">Upload or paste your Java code for AI-powered analysis and suggestions</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Code Input */}
        <div className="space-y-4">
          <div className="bg-white rounded-lg shadow-sm p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-gray-900">Code Input</h3>
              <div className="flex space-x-2">
                <label className="inline-flex items-center px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 cursor-pointer">
                  <Upload className="h-4 w-4 mr-2" />
                  Upload File
                  <input
                    type="file"
                    accept=".java"
                    onChange={handleFileUpload}
                    className="hidden"
                  />
                </label>
                <button
                  onClick={handleClear}
                  className="inline-flex items-center px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                >
                  Clear
                </button>
              </div>
            </div>

            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  AI Provider
                </label>
                <select
                  value={selectedProvider}
                  onChange={(e) => setSelectedProvider(e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  {availableProviders.map((provider) => (
                    <option key={provider} value={provider}>
                      {provider}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Java Code
                </label>
                <textarea
                  value={code}
                  onChange={(e) => setCode(e.target.value)}
                  placeholder="Paste your Java code here or upload a .java file..."
                  className="w-full h-96 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 font-mono text-sm"
                />
              </div>

              <button
                onClick={handleReview}
                disabled={isReviewing || !code.trim()}
                className="w-full inline-flex items-center justify-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {isReviewing ? (
                  <>
                    <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2"></div>
                    Reviewing...
                  </>
                ) : (
                  <>
                    <Play className="h-4 w-4 mr-2" />
                    Review Code
                  </>
                )}
              </button>
            </div>
          </div>
        </div>

        {/* Results */}
        <div className="space-y-4">
          {currentReview ? (
            <div className="bg-white rounded-lg shadow-sm p-6">
              <div className="flex items-center justify-between mb-4">
                <h3 className="text-lg font-semibold text-gray-900">Review Results</h3>
                <button
                  onClick={handleExport}
                  className="inline-flex items-center px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                >
                  <Download className="h-4 w-4 mr-2" />
                  Export
                </button>
              </div>

              <div className="space-y-4">
                {/* Summary */}
                <div className="bg-blue-50 p-4 rounded-lg">
                  <h4 className="font-medium text-blue-900 mb-2">Summary</h4>
                  <p className="text-blue-800">{currentReview.summary}</p>
                </div>

                {/* Statistics */}
                <div className="grid grid-cols-2 gap-4">
                  <div className="bg-red-50 p-3 rounded-lg">
                    <div className="flex items-center">
                      <AlertCircle className="h-5 w-5 text-red-600 mr-2" />
                      <span className="text-red-800 font-medium">Errors: {currentReview.errors?.length || 0}</span>
                    </div>
                  </div>
                  <div className="bg-yellow-50 p-3 rounded-lg">
                    <div className="flex items-center">
                      <AlertCircle className="h-5 w-5 text-yellow-600 mr-2" />
                      <span className="text-yellow-800 font-medium">Warnings: {currentReview.warnings?.length || 0}</span>
                    </div>
                  </div>
                  <div className="bg-blue-50 p-3 rounded-lg">
                    <div className="flex items-center">
                      <Lightbulb className="h-5 w-5 text-blue-600 mr-2" />
                      <span className="text-blue-800 font-medium">Suggestions: {currentReview.suggestions?.length || 0}</span>
                    </div>
                  </div>
                  <div className="bg-green-50 p-3 rounded-lg">
                    <div className="flex items-center">
                      <CheckCircle className="h-5 w-5 text-green-600 mr-2" />
                      <span className="text-green-800 font-medium">Good Practices: {currentReview.goodPractices?.length || 0}</span>
                    </div>
                  </div>
                </div>

                {/* Issues */}
                <div className="space-y-3">
                  {currentReview.errors?.length > 0 && (
                    <div className="border-l-4 border-red-500 bg-red-50 p-4">
                      <h4 className="font-medium text-red-900 mb-2">❌ Errors</h4>
                      <ul className="space-y-1">
                        {currentReview.errors.map((error, index) => (
                          <li key={index} className="text-red-800 text-sm">• {error}</li>
                        ))}
                      </ul>
                    </div>
                  )}

                  {currentReview.warnings?.length > 0 && (
                    <div className="border-l-4 border-yellow-500 bg-yellow-50 p-4">
                      <h4 className="font-medium text-yellow-900 mb-2">⚠️ Warnings</h4>
                      <ul className="space-y-1">
                        {currentReview.warnings.map((warning, index) => (
                          <li key={index} className="text-yellow-800 text-sm">• {warning}</li>
                        ))}
                      </ul>
                    </div>
                  )}

                  {currentReview.suggestions?.length > 0 && (
                    <div className="border-l-4 border-blue-500 bg-blue-50 p-4">
                      <h4 className="font-medium text-blue-900 mb-2">💡 Suggestions</h4>
                      <ul className="space-y-1">
                        {currentReview.suggestions.map((suggestion, index) => (
                          <li key={index} className="text-blue-800 text-sm">• {suggestion}</li>
                        ))}
                      </ul>
                    </div>
                  )}

                  {currentReview.goodPractices?.length > 0 && (
                    <div className="border-l-4 border-green-500 bg-green-50 p-4">
                      <h4 className="font-medium text-green-900 mb-2">✅ Good Practices</h4>
                      <ul className="space-y-1">
                        {currentReview.goodPractices.map((practice, index) => (
                          <li key={index} className="text-green-800 text-sm">• {practice}</li>
                        ))}
                      </ul>
                    </div>
                  )}
                </div>
              </div>
            </div>
          ) : (
            <div className="bg-white rounded-lg shadow-sm p-6">
              <div className="text-center py-12">
                <FileText className="h-12 w-12 text-gray-400 mx-auto mb-4" />
                <h3 className="text-lg font-medium text-gray-900 mb-2">No Review Yet</h3>
                <p className="text-gray-500">Upload or paste Java code and click "Review Code" to get started</p>
              </div>
            </div>
          )}
        </div>
      </div>

      {/* Recent Reviews */}
      {recentReviews.length > 0 && (
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Recent Reviews</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {recentReviews.slice(0, 3).map((review) => (
              <ReviewCard key={review.id} review={review} />
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default CodeReviewer;
