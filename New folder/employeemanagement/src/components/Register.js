import React, { useState } from 'react';
import { TextField, Button, Container } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:2001/auth/signup', { email, password });
            
            // Redirect to the login page after successful registration
            navigate('/login');
        } catch (error) {
            console.error('Registration failed:', error);
            alert('Registration failed');
        }
    };

    return (
        <Container>
            <h2>Register</h2>
            <form onSubmit={handleRegister}>
                <TextField label="Email" variant="outlined" fullWidth margin="normal"
                    value={email} onChange={(e) => setEmail(e.target.value)} />
                <TextField label="Password" type="password" variant="outlined" fullWidth margin="normal"
                    value={password} onChange={(e) => setPassword(e.target.value)} />
                <Button type="submit" variant="contained" color="primary">Register</Button>
            </form>
        </Container>
    );
};

export default Register;
