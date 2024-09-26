// src/components/EmployeeDashboard.js
import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { getEmployees, addEmployee, updateEmployee, deleteEmployee } from '../actions/employeeActions';
import { logoutUser, checkTokenExpiration } from '../actions/authActions';
import {
  Container, Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
  Dialog, DialogActions, DialogContent, DialogTitle, TextField, Alert, Snackbar
} from '@mui/material';

const EmployeeDashboard = () => {
  const dispatch = useDispatch();
  const employees = useSelector((state) => state.employees.employees);
  const [open, setOpen] = useState(false);
  const [currentEmployee, setCurrentEmployee] = useState({ id: '', firstName: '', lastName: '', email: '', department: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);  // Loading indicator state
  const [snackbarOpen, setSnackbarOpen] = useState(false);  // Snackbar state for notifications

  useEffect(() => {
    dispatch(checkTokenExpiration());  // Check token expiration on component load
    setLoading(true);
    dispatch(getEmployees()).finally(() => setLoading(false));  // Load employees with loading indicator
  }, [dispatch]);

  const handleDelete = (id) => {
    dispatch(deleteEmployee(id));
    setSnackbarOpen(true);  // Show success snackbar on delete
  };

  const handleDialogOpen = (employee = { id: '', firstName: '', lastName: '', email: '', department: '' }) => {
    setCurrentEmployee(employee);
    setOpen(true);
  };

  const handleDialogClose = () => {
    setOpen(false);
    setError('');
  };

  const handleSubmit = () => {
    if (!currentEmployee.firstName || !currentEmployee.lastName || !currentEmployee.email || !currentEmployee.department) {
      setError('All fields are required');
      return;
    }
    setError('');
    if (currentEmployee.id) {
      dispatch(updateEmployee(currentEmployee.id, currentEmployee));
    } else {
      dispatch(addEmployee(currentEmployee));
    }
    setSnackbarOpen(true);  // Show success snackbar on add/update
    handleDialogClose();
  };

  const handleLogout = () => {
    dispatch(logoutUser());
    window.location.href = '/login';  // Redirect to login on logout
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>Employee Dashboard</Typography>
      <Button variant="contained" color="primary" onClick={() => handleDialogOpen()}>Add Employee</Button>
      <Button variant="contained" color="secondary" onClick={handleLogout} style={{ marginLeft: '10px' }}>Logout</Button>

      {loading ? <p>Loading...</p> : (
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
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
                  <TableCell>{employee.id}</TableCell>
                  <TableCell>{employee.firstName}</TableCell>
                  <TableCell>{employee.lastName}</TableCell>
                  <TableCell>{employee.email}</TableCell>
                  <TableCell>{employee.department}</TableCell>
                  <TableCell>
                    <Button color="primary" onClick={() => handleDialogOpen(employee)}>Edit</Button>
                    <Button color="secondary" onClick={() => handleDelete(employee.id)}>Delete</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

      <Dialog open={open} onClose={handleDialogClose}>
        <DialogTitle>{currentEmployee.id ? 'Update Employee' : 'Add Employee'}</DialogTitle>
        <DialogContent>
          {error && <Alert severity="error">{error}</Alert>}
          <TextField
            label="First Name"
            fullWidth
            margin="normal"
            value={currentEmployee.firstName}
            onChange={(e) => setCurrentEmployee({ ...currentEmployee, firstName: e.target.value })}
          />
          <TextField
            label="Last Name"
            fullWidth
            margin="normal"
            value={currentEmployee.lastName}
            onChange={(e) => setCurrentEmployee({ ...currentEmployee, lastName: e.target.value })}
          />
          <TextField
            label="Email"
            fullWidth
            margin="normal"
            value={currentEmployee.email}
            onChange={(e) => setCurrentEmployee({ ...currentEmployee, email: e.target.value })}
          />
          <TextField
            label="Department"
            fullWidth
            margin="normal"
            value={currentEmployee.department}
            onChange={(e) => setCurrentEmployee({ ...currentEmployee, department: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose} color="secondary">Cancel</Button>
          <Button onClick={handleSubmit} color="primary">Save</Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={() => setSnackbarOpen(false)}
        message="Operation successful!"
      />
    </Container>
  );
};

export default EmployeeDashboard;
