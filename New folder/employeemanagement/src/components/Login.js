import React, { useState } from "react";
import { TextField, Button, Container } from "@mui/material";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [username, setusername] = useState("");
  const [password, setPassword] = useState("");
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

  return (
    <Container>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <TextField
          label="username"
          variant="outlined"
          fullWidth
          margin="normal"
          value={username}
          onChange={(e) => setusername(e.target.value)}
        />
        <TextField
          label="Password"
          type="password"
          variant="outlined"
          fullWidth
          margin="normal"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button type="submit" variant="contained" color="primary">
          Login
        </Button>
      </form>
      <Button 
        variant="contained" 
        color="secondary" 
        onClick={() => navigate('/register')} 
        style={{ marginTop: '20px' }}>
        Register
      </Button>
    </Container>
  );
};

export default Login;
