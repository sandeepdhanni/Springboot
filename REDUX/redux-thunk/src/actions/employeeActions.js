import axios from 'axios';

export const getEmployees = () => async (dispatch) => {
  try {
    const res = await axios.get('http://localhost:2001/api/employees', {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    });
    dispatch({ type: 'GET_EMPLOYEES', payload: res.data });
  } catch (error) {
    dispatch({ type: 'EMPLOYEE_ERROR', payload: error.response.data });
  }
};

export const addEmployee = (employee) => async (dispatch) => {
  try {
    const res = await axios.post('http://localhost:2001/api/employees', employee, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    });
    dispatch({ type: 'ADD_EMPLOYEE', payload: res.data });
  } catch (error) {
    dispatch({ type: 'EMPLOYEE_ERROR', payload: error.response.data });
  }
};

export const updateEmployee = (id, employee) => async (dispatch) => {
  try {
    const res = await axios.put(`http://localhost:2001/api/employees/${id}`, employee, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    });
    dispatch({ type: 'UPDATE_EMPLOYEE', payload: res.data });
  } catch (error) {
    dispatch({ type: 'EMPLOYEE_ERROR', payload: error.response.data });
  }
};

export const deleteEmployee = (id) => async (dispatch) => {
  try {
    await axios.delete(`http://localhost:2001/api/employees/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
    });
    dispatch({ type: 'DELETE_EMPLOYEE', payload: id });
  } catch (error) {
    dispatch({ type: 'EMPLOYEE_ERROR', payload: error.response.data });
  }
};