export const fetchEmployees = () => {
    return (dispatch) => {
        const employees = [
            { id: 1, name: 'John Doe', role: 'Developer' },
            { id: 2, name: 'Jane Smith', role: 'Manager' },
        ];
        dispatch({ type: 'FETCH_EMPLOYEES_SUCCESS', payload: employees });
    };
};

export const addEmployee = (employee) => {
    return (dispatch) => {
        dispatch({ type: 'ADD_EMPLOYEE', payload: employee });
    };
};

export const deleteEmployee = (id) => {
    return (dispatch) => {
        dispatch({ type: 'DELETE_EMPLOYEE', payload: id });
    };
};
