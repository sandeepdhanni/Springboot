export const GET_EMPLOYEES = 'GET_EMPLOYEES';
export const GET_EMPLOYEES_SUCCESS = 'GET_EMPLOYEES_SUCCESS';
export const GET_EMPLOYEES_FAILURE = 'GET_EMPLOYEES_FAILURE';

export const CREATE_EMPLOYEE = 'CREATE_EMPLOYEE';
export const CREATE_EMPLOYEE_SUCCESS = 'CREATE_EMPLOYEE_SUCCESS';
export const CREATE_EMPLOYEE_FAILURE = 'CREATE_EMPLOYEE_FAILURE';

export const UPDATE_EMPLOYEE = 'UPDATE_EMPLOYEE';
export const UPDATE_EMPLOYEE_SUCCESS = 'UPDATE_EMPLOYEE_SUCCESS';
export const UPDATE_EMPLOYEE_FAILURE = 'UPDATE_EMPLOYEE_FAILURE';

export const DELETE_EMPLOYEE = 'DELETE_EMPLOYEE';
export const DELETE_EMPLOYEE_SUCCESS = 'DELETE_EMPLOYEE_SUCCESS';
export const DELETE_EMPLOYEE_FAILURE = 'DELETE_EMPLOYEE_FAILURE';

export const getEmployees = () => ({ type: GET_EMPLOYEES });
export const createEmployee = (employee) => ({ type: CREATE_EMPLOYEE, payload: employee });
export const updateEmployee = (id, employee) => ({ type: UPDATE_EMPLOYEE, payload: { id, employee } });
export const deleteEmployee = (id) => ({ type: DELETE_EMPLOYEE, payload: id });
