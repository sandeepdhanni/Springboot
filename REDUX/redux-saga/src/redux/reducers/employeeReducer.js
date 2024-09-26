import {
    GET_EMPLOYEES_SUCCESS,
    CREATE_EMPLOYEE_SUCCESS,
    UPDATE_EMPLOYEE_SUCCESS,
    DELETE_EMPLOYEE_SUCCESS,
} from '../actions/employeeActions';

const initialState = {
    employees: [],
    loading: false,
    error: null,
};

const employeeReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_EMPLOYEES_SUCCESS:
            return { ...state, employees: action.payload };
        case CREATE_EMPLOYEE_SUCCESS:
            return { ...state, employees: [...state.employees, action.payload] };
        case UPDATE_EMPLOYEE_SUCCESS:
            return {
                ...state,
                employees: state.employees.map(emp => emp.id === action.payload.id ? action.payload : emp),
            };
        case DELETE_EMPLOYEE_SUCCESS:
            return {
                ...state,
                employees: state.employees.filter(emp => emp.id !== action.payload),
            };
        default:
            return state;
    }
};

export default employeeReducer;
