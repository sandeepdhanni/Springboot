import React, { useState } from 'react';
import { TextField, Button, Container } from '@mui/material';
import { useDispatch } from 'react-redux';
import { addEmployee } from '../../redux/actions/employeeActions';

const EmployeeForm = () => {
    const [name, setName] = useState('');
    const [role, setRole] = useState('');
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(addEmployee({ name, role }));
        setName('');
        setRole('');
    };

    return (
        <Container maxWidth="sm">
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Name"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <TextField
                    label="Role"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                />
                <Button type="submit" variant="contained" color="primary">
                    Add Employee
                </Button>
            </form>
        </Container>
    );
};

export default EmployeeForm;
