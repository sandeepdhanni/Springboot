import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { deleteEmployee } from '../../redux/actions/employeeActions';

const EmployeeTable = ({ employees }) => {
    const dispatch = useDispatch();

    const handleDelete = (id) => {
        dispatch(deleteEmployee(id));
    };

    return (
        <TableContainer>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Role</TableCell>
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {employees.map((employee) => (
                        <TableRow key={employee.id}>
                            <TableCell>{employee.name}</TableCell>
                            <TableCell>{employee.role}</TableCell>
                            <TableCell>
                                <Button color="secondary" onClick={() => handleDelete(employee.id)}>
                                    Delete
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default EmployeeTable;
