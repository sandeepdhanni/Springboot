import React, { useState } from 'react';
import { TextField, Button, Container } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleRegister = (e) => {
        e.preventDefault();
        // Handle registration logic here (e.g., API call)
        navigate('/login');
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
                <Button type="submit" variant="contained" color="primary">Login</Button>
            </form>
        </Container>
    );
};

export default Register;
