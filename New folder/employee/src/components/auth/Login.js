import React, { useState } from 'react';
import { TextField, Button, Container, Typography } from '@mui/material';
import { useDispatch } from 'react-redux';
import { loginUser } from '../../redux/actions/authActions';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(loginUser({ email, password }));
    };

    return (
        <Container maxWidth="sm">
            <Typography variant="h4">Login</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Email"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <TextField
                    label="Password"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button type="submit" variant="contained" color="primary">
                    Login
                </Button>
                <Button type="submit" variant="contained" color="primary">
                    Register here!
                </Button>
            </form>
        </Container>
    );
};

export default Login;
