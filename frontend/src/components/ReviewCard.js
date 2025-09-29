import React from 'react';
import { Calendar, FileText, AlertCircle, CheckCircle, Lightbulb, Shield } from 'lucide-react';

const ReviewCard = ({ review }) => {
  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getProviderColor = (provider) => {
    switch (provider) {
      case 'OpenAI GPT-4':
        return 'bg-green-100 text-green-800';
      case 'Hugging Face Code Llama':
        return 'bg-blue-100 text-blue-800';
      case 'Anthropic Claude':
        return 'bg-purple-100 text-purple-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="bg-white border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between mb-3">
        <div className="flex items-center space-x-2">
          <FileText className="h-4 w-4 text-gray-500" />
          <span className="text-sm font-medium text-gray-900">
            {review.fileName || 'Untitled.java'}
          </span>
        </div>
        <span className={`px-2 py-1 rounded-full text-xs font-medium ${getProviderColor(review.aiProvider)}`}>
          {review.aiProvider}
        </span>
      </div>

      <p className="text-sm text-gray-600 mb-3 line-clamp-2">
        {review.summary}
      </p>

      <div className="flex items-center justify-between text-xs text-gray-500 mb-3">
        <div className="flex items-center space-x-1">
          <Calendar className="h-3 w-3" />
          <span>{formatDate(review.reviewTime)}</span>
        </div>
        <span className="font-medium">{review.totalIssues} issues</span>
      </div>

      <div className="grid grid-cols-4 gap-2">
        <div className="flex items-center space-x-1">
          <AlertCircle className="h-3 w-3 text-red-500" />
          <span className="text-xs text-red-600">{review.errors?.length || 0}</span>
        </div>
        <div className="flex items-center space-x-1">
          <AlertCircle className="h-3 w-3 text-yellow-500" />
          <span className="text-xs text-yellow-600">{review.warnings?.length || 0}</span>
        </div>
        <div className="flex items-center space-x-1">
          <Lightbulb className="h-3 w-3 text-blue-500" />
          <span className="text-xs text-blue-600">{review.suggestions?.length || 0}</span>
        </div>
        <div className="flex items-center space-x-1">
          <CheckCircle className="h-3 w-3 text-green-500" />
          <span className="text-xs text-green-600">{review.goodPractices?.length || 0}</span>
        </div>
      </div>
    </div>
  );
};

export default ReviewCard;
