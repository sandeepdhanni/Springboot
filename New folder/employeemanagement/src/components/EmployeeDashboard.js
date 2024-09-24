import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, TextField, Container } from '@mui/material';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const EmployeeDashboard = () => {
    const [employees, setEmployees] = useState([]);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [department, setDepartment] = useState('');
    const [id, setId] = useState(null);

    const navigate = useNavigate();


    useEffect(() => {
        fetchEmployees();
    }, []);

    // Fetch employees with JWT token
    const fetchEmployees = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get('http://localhost:2001/api/employees', {
                headers: {
                    Authorization: `Bearer ${token}` // Include JWT token
                }
            });
            setEmployees(response.data);
        } catch (error) {
            console.error('Error fetching employees:', error);
            if (error.response.status === 401) {
                alert('Unauthorized. Please log in again.');
            }
        }
    };

    const handleAddOrUpdate = async () => {
        const token = localStorage.getItem('token');
        const employeeData = { firstName, lastName, email, department };
        
        try {
            if (id) {
                await axios.put(`http://localhost:2001/api/employees/${id}`, employeeData, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
            } else {
                await axios.post('http://localhost:2001/api/employees', employeeData, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
            }
            fetchEmployees();
            resetForm();
        } catch (error) {
            console.error('Error saving employee:', error);
        }
    };

    const handleEdit = (employee) => {
        setId(employee.id);
        setFirstName(employee.firstName);
        setLastName(employee.lastName);
        setEmail(employee.email);
        setDepartment(employee.department);
    };

    const handleDelete = async (employeeId) => {
        const token = localStorage.getItem('token');
        try {
            await axios.delete(`http://localhost:2001/api/employees/${employeeId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            fetchEmployees();
        } catch (error) {
            console.error('Error deleting employee:', error);
        }
    };

     // Clear token and redirect to login
    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const resetForm = () => {
        setId(null);
        setFirstName('');
        setLastName('');
        setEmail('');
        setDepartment('');
    };

    return (
        <Container>
            <h2>Employee Dashboard</h2>
            <Button onClick={handleLogout} variant="contained" color="secondary" style={{ marginBottom: '20px' }}>
                Logout
            </Button>
            <TextField label="First Name" variant="outlined" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
            <TextField label="Last Name" variant="outlined" value={lastName} onChange={(e) => setLastName(e.target.value)} />
            <TextField label="Email" variant="outlined" value={email} onChange={(e) => setEmail(e.target.value)} />
            <TextField label="Department" variant="outlined" value={department} onChange={(e) => setDepartment(e.target.value)} />
            <Button onClick={handleAddOrUpdate} variant="contained" color="primary">Add/Update</Button>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>First Name</TableCell>
                            <TableCell>Last Name</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Department</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {employees.map((employee) => (
                            <TableRow key={employee.id}>
                                <TableCell>{employee.firstName}</TableCell>
                                <TableCell>{employee.lastName}</TableCell>
                                <TableCell>{employee.email}</TableCell>
                                <TableCell>{employee.department}</TableCell>
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
