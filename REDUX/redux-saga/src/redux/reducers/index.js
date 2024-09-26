import { combineReducers } from 'redux';
import employeeReducer from './employeeReducer';

const rootReducer = combineReducers({
    employee: employeeReducer,
});

export default rootReducer;
