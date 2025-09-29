import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

class CalculatorService {
  /**
   * Calculate using query parameters (GET method)
   */
  static async calculateWithQuery(operand1, operand2, operator) {
    try {
      const response = await axios.get(`${API_BASE_URL}/calculator/calc`, {
        params: {
          operand1: operand1,
          operand2: operand2,
          operator: operator
        }
      });
      return response.data;
    } catch (error) {
      console.error('Error calculating with query params:', error);
      throw new Error(error.response?.data?.error || 'Failed to calculate');
    }
  }

  /**
   * Calculate using JSON body (POST method)
   */
  static async calculateWithPost(operand1, operand2, operator) {
    try {
      const response = await axios.post(`${API_BASE_URL}/calculator/calc`, {
        a: Number(operand1),
        b: Number(operand2),
        op: operator
      });
      return response.data;
    } catch (error) {
      console.error('Error calculating with POST:', error);
      throw new Error(error.response?.data?.error || 'Failed to calculate');
    }
  }

  /**
   * Calculate using path parameters
   */
  static async calculateWithPath(operand1, operand2, operator) {
    try {
      // Handle division operator encoding
      const encodedOperator = operator === '/' ? '%2F' : operator;
      const response = await axios.get(`${API_BASE_URL}/calculator/calc/${operand1}/${operand2}/${encodedOperator}`);
      return response.data;
    } catch (error) {
      console.error('Error calculating with path params:', error);
      throw new Error(error.response?.data?.error || 'Failed to calculate');
    }
  }

  /**
   * Get supported operations
   */
  static async getSupportedOperations() {
    try {
      const response = await axios.get(`${API_BASE_URL}/calculator/operations`);
      return response.data;
    } catch (error) {
      console.error('Error fetching operations:', error);
      return {
        operators: ['+', '-', '*', '/', 'add', 'sub', 'mul', 'div'],
        methods: ['Query Parameters', 'JSON Body', 'Path Parameters'],
        description: 'AWS Lambda-powered calculator with multiple input methods'
      };
    }
  }

  /**
   * Check calculator service health
   */
  static async checkHealth() {
    try {
      const response = await axios.get(`${API_BASE_URL}/calculator/health`);
      return response.data;
    } catch (error) {
      console.error('Error checking calculator health:', error);
      return {
        status: 'DOWN',
        service: 'AWS Lambda Calculator',
        error: error.message
      };
    }
  }

  /**
   * Validate operator
   */
  static isValidOperator(operator) {
    const validOperators = ['+', '-', '*', '/', 'add', 'sub', 'mul', 'div'];
    return validOperators.includes(operator.toLowerCase());
  }

  /**
   * Format calculation result for display
   */
  static formatResult(result) {
    if (!result) return 'No result';
    
    if (result.result) {
      return result.result;
    }
    
    if (result.input && result.output) {
      return `${result.input.a} ${result.input.op} ${result.input.b} = ${result.output.c}`;
    }
    
    if (result.a && result.b && result.op && result.c) {
      return `${result.a} ${result.op} ${result.b} = ${result.c}`;
    }
    
    return JSON.stringify(result);
  }
}

export { CalculatorService };