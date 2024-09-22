const initialState = {
    employees: [],
    error: null,
};

const employeeReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'FETCH_EMPLOYEES_SUCCESS':
            return { ...state, employees: action.payload, error: null };
        case 'FETCH_EMPLOYEES_FAILURE':
            return { ...state, error: action.payload };
        case 'ADD_EMPLOYEE':
            return { ...state, employees: [...state.employees, action.payload] };
        case 'DELETE_EMPLOYEE':
            return {
                ...state,
                employees: state.employees.filter(emp => emp.id !== action.payload),
            };
        default:
            return state;
    }
};

export default employeeReducer;
