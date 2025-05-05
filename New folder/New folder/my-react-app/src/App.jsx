import './App.css';
import TextBox from './TextBox';
import MfaLogin from './components/MfaLogin';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MobileVerifyPage from './pages/MobileVerifyPage';
import LoginPage from './pages/LoginPage';


function App() {
  return (
    // <div>
    //   {/* <TextBox /> */}
    //   <MfaLogin />
    // </div>
    <Router>
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/verify/:mfaId" element={<MobileVerifyPage />} />
    </Routes>
  </Router>
  );
}

export default App;
