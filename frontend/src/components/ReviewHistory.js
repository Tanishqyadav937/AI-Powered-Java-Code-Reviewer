import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { Search, Trash2, Download, RefreshCw } from 'lucide-react';

import { CodeReviewService } from '../services/CodeReviewService';
import ReviewCard from './ReviewCard';

const ReviewHistory = ({ reviews, onLoadReviews, loading }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredReviews, setFilteredReviews] = useState(reviews);
  const [selectedProvider, setSelectedProvider] = useState('all');

  useEffect(() => {
    setFilteredReviews(reviews);
  }, [reviews]);

  useEffect(() => {
    filterReviews();
  }, [searchTerm, selectedProvider, reviews]);

  const filterReviews = () => {
    let filtered = reviews;

    if (searchTerm) {
      filtered = filtered.filter(review =>
        review.summary.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (review.fileName && review.fileName.toLowerCase().includes(searchTerm.toLowerCase()))
      );
    }

    if (selectedProvider !== 'all') {
      filtered = filtered.filter(review => review.aiProvider === selectedProvider);
    }

    setFilteredReviews(filtered);
  };

  const handleDelete = async (reviewId) => {
    if (window.confirm('Are you sure you want to delete this review?')) {
      try {
        await CodeReviewService.deleteReview(reviewId);
        onLoadReviews();
        toast.success('Review deleted successfully');
      } catch (error) {
        toast.error('Failed to delete review');
      }
    }
  };

  const handleExport = (review) => {
    const report = generateReport(review);
    const blob = new Blob([report], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `code-review-${review.id}.txt`;
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
Generated: ${new Date(review.reviewTime).toLocaleString()}
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
    `.trim();
  };

  const getUniqueProviders = () => {
    const providers = [...new Set(reviews.map(review => review.aiProvider))];
    return providers;
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Review History</h2>
            <p className="text-gray-600">View and manage your previous code reviews</p>
          </div>
          <button
            onClick={onLoadReviews}
            disabled={loading}
            className="inline-flex items-center px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50"
          >
            <RefreshCw className={`h-4 w-4 mr-2 ${loading ? 'animate-spin' : ''}`} />
            Refresh
          </button>
        </div>
      </div>

      {/* Filters */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Search Reviews
            </label>
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
              <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                placeholder="Search by summary or filename..."
                className="w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Filter by AI Provider
            </label>
            <select
              value={selectedProvider}
              onChange={(e) => setSelectedProvider(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="all">All Providers</option>
              {getUniqueProviders().map((provider) => (
                <option key={provider} value={provider}>
                  {provider}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {/* Statistics */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <h3 className="text-lg font-semibold text-gray-900 mb-4">Statistics</h3>
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div className="bg-blue-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-blue-600">{reviews.length}</div>
            <div className="text-sm text-blue-800">Total Reviews</div>
          </div>
          <div className="bg-red-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-red-600">
              {reviews.reduce((sum, review) => sum + (review.errors?.length || 0), 0)}
            </div>
            <div className="text-sm text-red-800">Total Errors</div>
          </div>
          <div className="bg-yellow-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-yellow-600">
              {reviews.reduce((sum, review) => sum + (review.warnings?.length || 0), 0)}
            </div>
            <div className="text-sm text-yellow-800">Total Warnings</div>
          </div>
          <div className="bg-green-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-green-600">
              {reviews.reduce((sum, review) => sum + (review.suggestions?.length || 0), 0)}
            </div>
            <div className="text-sm text-green-800">Total Suggestions</div>
          </div>
        </div>
      </div>

      {/* Reviews List */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-lg font-semibold text-gray-900">
            Reviews ({filteredReviews.length})
          </h3>
        </div>

        {loading ? (
          <div className="text-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600 mx-auto"></div>
            <p className="text-gray-500 mt-2">Loading reviews...</p>
          </div>
        ) : filteredReviews.length === 0 ? (
          <div className="text-center py-12">
            <div className="text-gray-400 mb-4">
              <Search className="h-12 w-12 mx-auto" />
            </div>
            <h3 className="text-lg font-medium text-gray-900 mb-2">No reviews found</h3>
            <p className="text-gray-500">
              {searchTerm || selectedProvider !== 'all' 
                ? 'Try adjusting your search or filter criteria'
                : 'Start by reviewing some Java code to see your history here'
              }
            </p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {filteredReviews.map((review) => (
              <div key={review.id} className="relative group">
                <ReviewCard review={review} />
                <div className="absolute top-2 right-2 opacity-0 group-hover:opacity-100 transition-opacity">
                  <div className="flex space-x-1">
                    <button
                      onClick={() => handleExport(review)}
                      className="p-1 bg-white rounded shadow-sm hover:bg-gray-50"
                      title="Export Report"
                    >
                      <Download className="h-4 w-4 text-gray-600" />
                    </button>
                    <button
                      onClick={() => handleDelete(review.id)}
                      className="p-1 bg-white rounded shadow-sm hover:bg-red-50"
                      title="Delete Review"
                    >
                      <Trash2 className="h-4 w-4 text-red-600" />
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ReviewHistory;
