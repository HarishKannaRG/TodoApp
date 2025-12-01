import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
// import './index.css'
// import App_default from './App_default.jsx'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
