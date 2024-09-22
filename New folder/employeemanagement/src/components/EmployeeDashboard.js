import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, TextField, Container } from '@mui/material';
import axios from 'axios';

const EmployeeDashboard = () => {
    const [employees, setEmployees] = useState([]);
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [id, setId] = useState(null);

    useEffect(() => {
        fetchEmployees();
    }, []);

    const fetchEmployees = async () => {
        // Replace with your API endpoint
        const response = await axios.get('http://localhost:2001/api/employees');
        setEmployees(response.data);
    };

    const handleAddOrUpdate = async () => {
        const employeeData = { name, email };
        if (id) {
            await axios.put(`http://localhost:2001/api/employees/${id}`, employeeData);
        } else {
            await axios.post('http://localhost:2001/api/employees', employeeData);
        }
        fetchEmployees();
        resetForm();
    };

    const handleEdit = (employee) => {
        setId(employee.id);
        setName(employee.name);
        setEmail(employee.email);
    };

    const handleDelete = async (employeeId) => {
        await axios.delete(`http://localhost:2001/api/employees/${employeeId}`);
        fetchEmployees();
    };

    const resetForm = () => {
        setId(null);
        setName('');
        setEmail('');
    };

    return (
        <Container>
            <h2>Employee Dashboard</h2>
            <TextField label="Name" variant="outlined" value={name} onChange={(e) => setName(e.target.value)} />
            <TextField label="Email" variant="outlined" value={email} onChange={(e) => setEmail(e.target.value)} />
            <Button onClick={handleAddOrUpdate} variant="contained" color="primary">Add/Update</Button>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {employees.map((employee) => (
                            <TableRow key={employee.id}>
                                <TableCell>{employee.name}</TableCell>
                                <TableCell>{employee.email}</TableCell>
                                <TableCell>
                                    <Button onClick={() => handleEdit(employee)}>Edit</Button>
                                    <Button onClick={() => handleDelete(employee.id)}>Delete</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    );
};

export default EmployeeDashboard;
