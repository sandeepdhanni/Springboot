const initialState = {
    employees: [],
    error: null,
  };
  
  const employeeReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'GET_EMPLOYEES':
        return { ...state, employees: action.payload };
      case 'ADD_EMPLOYEE':
        return { ...state, employees: [...state.employees, action.payload] };
      case 'UPDATE_EMPLOYEE':
        return {
          ...state,
          employees: state.employees.map((emp) =>
            emp.id === action.payload.id ? action.payload : emp
          ),
        };
      case 'DELETE_EMPLOYEE':
        return {
          ...state,
          employees: state.employees.filter((emp) => emp.id !== action.payload),
        };
      case 'EMPLOYEE_ERROR':
        return { ...state, error: action.payload };
      default:
        return state;
    }
  };
  
  export default employeeReducer;