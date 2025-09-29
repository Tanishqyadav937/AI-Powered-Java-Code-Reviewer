#!/usr/bin/env node

const { spawn } = require('child_process');
const path = require('path');

console.log('🚀 Starting AI Java Code Reviewer - Full Stack Application');
console.log('==========================================================');

// Start Backend Server (Spring Boot)
console.log('\n📡 Starting Backend Server (Port 8080)...');
const backend = spawn('mvnw.cmd', ['spring-boot:run'], {
  cwd: path.join(__dirname, 'backend'),
  stdio: 'pipe',
  shell: true
});

backend.stdout.on('data', (data) => {
  console.log(`[BACKEND] ${data.toString().trim()}`);
});

backend.stderr.on('data', (data) => {
  console.error(`[BACKEND ERROR] ${data.toString().trim()}`);
});

// Start Frontend Server (React)
console.log('🌐 Starting Frontend Server (Port 3000)...');
const frontend = spawn('npm', ['start'], {
  cwd: path.join(__dirname, 'frontend'),
  stdio: 'pipe',
  shell: true
});

frontend.stdout.on('data', (data) => {
  console.log(`[FRONTEND] ${data.toString().trim()}`);
});

frontend.stderr.on('data', (data) => {
  console.error(`[FRONTEND ERROR] ${data.toString().trim()}`);
});

// Handle process termination
process.on('SIGINT', () => {
  console.log('\n🛑 Shutting down servers...');
  backend.kill('SIGINT');
  frontend.kill('SIGINT');
  process.exit(0);
});

process.on('SIGTERM', () => {
  console.log('\n🛑 Shutting down servers...');
  backend.kill('SIGTERM');
  frontend.kill('SIGTERM');
  process.exit(0);
});

// Wait for servers to start
setTimeout(() => {
  console.log('\n✅ Servers are starting up...');
  console.log('📡 Backend API: http://localhost:8080');
  console.log('🌐 Frontend App: http://localhost:3000');
  console.log('📊 H2 Database Console: http://localhost:8080/h2-console');
  console.log('\n💡 Press Ctrl+C to stop both servers');
}, 2000);

