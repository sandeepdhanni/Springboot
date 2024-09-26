import { configureStore } from '@reduxjs/toolkit';
import authReducer from './reducers/authReducer';
import employeeReducer from './reducers/employeeReducer';

const store = configureStore({
  reducer: {
    auth: authReducer,
    employees: employeeReducer,
  },
});

export default store;