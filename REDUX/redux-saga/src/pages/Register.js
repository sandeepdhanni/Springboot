// import React, { useState } from 'react';
// import { TextField, Button } from '@mui/material';
// import axios from 'axios';
// import { useNavigate } from "react-router-dom";


// const Register = () => {
//     const [username, setUsername] = useState('');
//     const [password, setPassword] = useState('');
//     const [role, setRole] = useState('');
//     const navigate = useNavigate();

//     const handleRegister = async (e) => {
//         e.preventDefault();
//         try {
//             await axios.post('http://localhost:2001/auth/signup', { username, password, role });
//             navigate('/login');
//         } catch (error) {
//             console.error('Registration failed:', error);
//             alert('Registration failed');
//         }
//     };

//     return (
//         <div>
//             <h2>Register</h2>
//             <TextField label="Username" onChange={(e) => setUsername(e.target.value)} />
//             <TextField label="Password" type="password" onChange={(e) => setPassword(e.target.value)} />
//             <TextField label="Role" onChange={(e) => setRole(e.target.value)} />
//             <Button onClick={handleRegister}>Register</Button>
//         </div>
//     );
// };

// export default Register;
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/styles.css'; // Import the CSS file

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [firstname, setFirstname] = useState('');
  const [role, setRole] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:2001/auth/signup', { username, password, role, firstname });
      navigate('/login');
    } catch (error) {
      alert('Registration failed. Please try again.');
    }
  };

  return (
    <div className="container">
      <form className="form" onSubmit={handleRegister}>
        <h2 className="form-title">Register</h2>
        <input 
          type="text" 
          className="input-field" 
          placeholder="FirstName" 
          value={firstname} 
          onChange={(e) => setFirstname(e.target.value)} 
        />
        <input 
          type="text" 
          className="input-field" 
          placeholder="Username" 
          value={username} 
          onChange={(e) => setUsername(e.target.value)} 
        />
        <input 
          type="password" 
          className="input-field" 
          placeholder="Password" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
        />
        <input 
          type="text" 
          className="input-field" 
          placeholder="Role" 
          value={role} 
          onChange={(e) => setRole(e.target.value)} 
        />
        <button type="submit" className="submit-btn">Register</button>
      </form>
    </div>
  );
};

export default Register;
