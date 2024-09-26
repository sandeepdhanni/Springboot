// import React, { useState } from 'react';
// import { TextField, Button } from '@mui/material';
// import axios from 'axios';
// import { useNavigate } from "react-router-dom";


// const Login = () => {
//     const [username, setUsername] = useState('');
//     const [password, setPassword] = useState('');
//     const navigate = useNavigate();

//     const handleLogin = async (e) => {
//         e.preventDefault();
//         try {
//           console.log("Sending login request with:", { username, password });
    
//           const response = await axios.post("http://localhost:2001/auth/login", {
//             username,
//             password,
//           });
    
//           console.log("Login response:", response);
//           const token  = response.data;
//           console.log(token);
    
//           localStorage.setItem("token", token);
    
//           navigate("/dashboard");
//         } catch (error) {
//           console.error("Login failed:", error);
//           alert("Login failed. Please check your credentials.");
//         }
//     };

//     return (
//         <div>
//             <h2>Login</h2>
//             <TextField label="Username" onChange={(e) => setUsername(e.target.value)} />
//             <TextField label="Password" type="password" onChange={(e) => setPassword(e.target.value)} />
//             <Button onClick={handleLogin}>Login</Button>
//         </div>
//     );
// };

// export default Login;
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/styles.css'; // Import the CSS file

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();


  const handleLogin = async (e) => {
            e.preventDefault();
            try {
              console.log("Sending login request with:", { username, password });
        
              const response = await axios.post("http://localhost:2001/auth/login", {
                username,
                password,
              });
        
              console.log("Login response:", response);
              const token  = response.data;
              console.log(token);
        
              localStorage.setItem("token", token);
        
              navigate("/dashboard");
            } catch (error) {
              console.error("Login failed:", error);
              alert("Login failed. Please check your credentials.");
            }
        };

  // const handleLogin = async (e) => {
  //   e.preventDefault();
  //   try {
  //     const response = await axios.post('http://localhost:2001/auth/login', { username, password });
  //     localStorage.setItem('token', response.data.token);
  //     navigate('/dashboard');
  //   } catch (error) {
  //     alert('Login failed. Please check your credentials.');
  //   }
  // };

  return (
    <div className="container">
      <form className="form" onSubmit={handleLogin}>
        <h2 className="form-title">Login</h2>
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
        <button type="submit" className="submit-btn">Login</button>
      </form>
    </div>
  );
};

export default Login;

