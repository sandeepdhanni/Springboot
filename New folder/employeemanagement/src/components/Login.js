import React, { useState } from 'react';
import { TextField, Button, Container } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        // Handle login logic here (e.g., API call)
        navigate('/dashboard');
    };

    return (
        <Container>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <TextField label="Email" variant="outlined" fullWidth margin="normal"
                    value={email} onChange={(e) => setEmail(e.target.value)} />
                <TextField label="Password" type="password" variant="outlined" fullWidth margin="normal"
                    value={password} onChange={(e) => setPassword(e.target.value)} />
                <Button type="submit" variant="contained" color="primary">Login</Button>
                <Button type="submit" variant="contained" color="primary">Register</Button>
            </form>
        </Container>
    );
};

export default Login;
