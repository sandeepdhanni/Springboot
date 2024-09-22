import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import EmployeeForm from './EmployeeForm';
import EmployeeTable from './EmployeeTable';
import { fetchEmployees } from '../../redux/actions/employeeActions';

const Dashboard = () => {
    const dispatch = useDispatch();
    const employees = useSelector((state) => state.employees.data);

    useEffect(() => {
        dispatch(fetchEmployees());
    }, [dispatch]);

    return (
        <div>
            <EmployeeForm />
            <EmployeeTable employees={employees} />
        </div>
    );
};

export default Dashboard;
