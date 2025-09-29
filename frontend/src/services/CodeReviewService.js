import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

class CodeReviewService {
  static async reviewCode(code, aiProvider, fileName = '') {
    try {
      const response = await axios.post(`${API_BASE_URL}/reviews/review`, {
        code,
        aiProvider,
        fileName
      });
      return response.data;
    } catch (error) {
      console.error('Error reviewing code:', error);
      throw new Error(error.response?.data?.errorMessage || 'Failed to review code');
    }
  }

  static async getAllReviews() {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews`);
      return response.data;
    } catch (error) {
      console.error('Error fetching reviews:', error);
      throw new Error('Failed to fetch reviews');
    }
  }

  static async getReviewById(id) {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching review:', error);
      throw new Error('Failed to fetch review');
    }
  }

  static async getRecentReviews() {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews/recent`);
      return response.data;
    } catch (error) {
      console.error('Error fetching recent reviews:', error);
      return [];
    }
  }

  static async getReviewsByProvider(provider) {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews/provider/${provider}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching reviews by provider:', error);
      throw new Error('Failed to fetch reviews by provider');
    }
  }

  static async searchReviews(keyword) {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews/search?keyword=${encodeURIComponent(keyword)}`);
      return response.data;
    } catch (error) {
      console.error('Error searching reviews:', error);
      throw new Error('Failed to search reviews');
    }
  }

  static async deleteReview(id) {
    try {
      await axios.delete(`${API_BASE_URL}/reviews/${id}`);
    } catch (error) {
      console.error('Error deleting review:', error);
      throw new Error('Failed to delete review');
    }
  }

  static async getAvailableProviders() {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews/providers`);
      return response.data;
    } catch (error) {
      console.error('Error fetching providers:', error);
      return ['OpenAI GPT-4'];
    }
  }

  static async getStatistics() {
    try {
      const response = await axios.get(`${API_BASE_URL}/reviews/stats`);
      return response.data;
    } catch (error) {
      console.error('Error fetching statistics:', error);
      throw new Error('Failed to fetch statistics');
    }
  }
}

export { CodeReviewService };
