import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { Save, Key, Info, AlertCircle } from 'lucide-react';

const Settings = () => {
  const [settings, setSettings] = useState({
    openaiApiKey: '',
    defaultProvider: 'OpenAI GPT-4',
    autoSave: true,
    theme: 'light',
    demoMode: true
  });

  useEffect(() => {
    loadSettings();
  }, []);

  const loadSettings = () => {
    // Load settings from localStorage
    const savedSettings = localStorage.getItem('codeReviewerSettings');
    if (savedSettings) {
      setSettings(JSON.parse(savedSettings));
    }
  };

  const handleSave = () => {
    localStorage.setItem('codeReviewerSettings', JSON.stringify(settings));
    toast.success('Settings saved successfully!');
  };

  const handleInputChange = (field, value) => {
    setSettings(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleReset = () => {
    if (window.confirm('Are you sure you want to reset all settings?')) {
      setSettings({
        openaiApiKey: '',
        defaultProvider: 'OpenAI GPT-4',
        autoSave: true,
        theme: 'light',
        demoMode: true
      });
      localStorage.removeItem('codeReviewerSettings');
      toast.success('Settings reset successfully!');
    }
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <h2 className="text-2xl font-bold text-gray-900 mb-2">Settings</h2>
        <p className="text-gray-600">Configure your OpenAI API key and application preferences. Works in demo mode without API key.</p>
      </div>

      {/* API Keys Section */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="flex items-center mb-4">
          <Key className="h-5 w-5 text-gray-500 mr-2" />
          <h3 className="text-lg font-semibold text-gray-900">API Keys</h3>
        </div>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              OpenAI API Key
            </label>
            <input
              type="password"
              value={settings.openaiApiKey}
              onChange={(e) => handleInputChange('openaiApiKey', e.target.value)}
              placeholder="sk-..."
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <p className="text-xs text-gray-500 mt-1">
              Get your API key from <a href="https://platform.openai.com/api-keys" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:underline">OpenAI Platform</a>
            </p>
            <div className="mt-3 p-3 bg-blue-50 border border-blue-200 rounded-md">
              <p className="text-sm text-blue-800">
                💡 <strong>Demo Mode Available:</strong> Leave empty to use intelligent demo analysis without API costs.
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Application Settings */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="flex items-center mb-4">
          <Info className="h-5 w-5 text-gray-500 mr-2" />
          <h3 className="text-lg font-semibold text-gray-900">Application Settings</h3>
        </div>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Default AI Provider
            </label>
            <select
              value={settings.defaultProvider}
              onChange={(e) => handleInputChange('defaultProvider', e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="OpenAI GPT-4">OpenAI GPT-4 (with Demo Fallback)</option>
            </select>
            <p className="text-xs text-gray-500 mt-1">
              Uses OpenAI when API key is provided, otherwise intelligent demo mode
            </p>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Theme
            </label>
            <select
              value={settings.theme}
              onChange={(e) => handleInputChange('theme', e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="light">Light</option>
              <option value="dark">Dark</option>
              <option value="auto">Auto</option>
            </select>
          </div>

          <div className="space-y-3">
            <div className="flex items-center">
              <input
                type="checkbox"
                id="demoMode"
                checked={settings.demoMode}
                onChange={(e) => handleInputChange('demoMode', e.target.checked)}
                className="h-4 w-4 text-green-600 focus:ring-green-500 border-gray-300 rounded"
              />
              <label htmlFor="demoMode" className="ml-2 block text-sm text-gray-700">
                Enable Demo Mode (works without API key)
              </label>
            </div>
            
            <div className="flex items-center">
              <input
                type="checkbox"
                id="autoSave"
                checked={settings.autoSave}
                onChange={(e) => handleInputChange('autoSave', e.target.checked)}
                className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              />
              <label htmlFor="autoSave" className="ml-2 block text-sm text-gray-700">
                Auto-save reviews to history
              </label>
            </div>
          </div>
        </div>
      </div>

      {/* Security & Demo Notice */}
      <div className="space-y-4">
        <div className="bg-green-50 border border-green-200 rounded-lg p-4">
          <div className="flex items-start">
            <AlertCircle className="h-5 w-5 text-green-600 mr-3 mt-0.5" />
            <div>
              <h4 className="text-sm font-medium text-green-800">Demo Mode Available</h4>
              <p className="text-sm text-green-700 mt-1">
                🚀 <strong>No API key required!</strong> Our intelligent demo mode provides realistic code analysis using advanced static analysis. 
                Perfect for testing and demonstrations without any costs.
              </p>
            </div>
          </div>
        </div>
        
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
          <div className="flex items-start">
            <AlertCircle className="h-5 w-5 text-blue-600 mr-3 mt-0.5" />
            <div>
              <h4 className="text-sm font-medium text-blue-800">AWS Lambda Calculator</h4>
              <p className="text-sm text-blue-700 mt-1">
                🧮 Built-in calculator powered by AWS Lambda API. 
                Access via <strong>/api/calculator/calc</strong> - no additional setup required.
              </p>
            </div>
          </div>
        </div>
        
        <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
          <div className="flex items-start">
            <AlertCircle className="h-5 w-5 text-yellow-600 mr-3 mt-0.5" />
            <div>
              <h4 className="text-sm font-medium text-yellow-800">Security Notice</h4>
              <div className="text-sm text-yellow-700 mt-1 space-y-2">
                <p>🔒 <strong>Your API keys are stored locally</strong> in your browser and are never sent to our servers.</p>
                <p>⚠️ Keep your API keys secure and never share them publicly or commit them to version control.</p>
                <p>💡 When demo mode is enabled, no API calls are made - everything runs locally.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Action Buttons */}
      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="flex items-center justify-between">
          <button
            onClick={handleReset}
            className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
          >
            Reset Settings
          </button>
          <button
            onClick={handleSave}
            className="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
          >
            <Save className="h-4 w-4 mr-2" />
            Save Settings
          </button>
        </div>
      </div>
    </div>
  );
};

export default Settings;

