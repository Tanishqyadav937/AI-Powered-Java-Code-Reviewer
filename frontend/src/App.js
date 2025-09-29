import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import Header from './components/Header';
import CodeReviewer from './components/CodeReviewer';
import ReviewHistory from './components/ReviewHistory';
import Settings from './components/Settings';
import { CodeReviewService } from './services/CodeReviewService';

function App() {
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadRecentReviews();
  }, []);

  const loadRecentReviews = async () => {
    try {
      setLoading(true);
      const recentReviews = await CodeReviewService.getRecentReviews();
      setReviews(recentReviews);
    } catch (error) {
      console.error('Error loading recent reviews:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleNewReview = (newReview) => {
    setReviews(prev => [newReview, ...prev]);
  };

  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Header />
        
        <main className="container mx-auto px-4 py-8">
          <Routes>
            <Route 
              path="/" 
              element={
                <CodeReviewer 
                  onNewReview={handleNewReview}
                  recentReviews={reviews}
                />
              } 
            />
            <Route 
              path="/history" 
              element={
                <ReviewHistory 
                  reviews={reviews}
                  onLoadReviews={loadRecentReviews}
                  loading={loading}
                />
              } 
            />
            <Route path="/settings" element={<Settings />} />
          </Routes>
        </main>

        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
        />
      </div>
    </Router>
  );
}

export default App;
