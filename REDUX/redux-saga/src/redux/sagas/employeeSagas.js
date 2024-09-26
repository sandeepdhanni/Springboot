import { call, put, takeEvery } from 'redux-saga/effects';
import axios from 'axios';
import {
    GET_EMPLOYEES,
    GET_EMPLOYEES_SUCCESS,
    GET_EMPLOYEES_FAILURE,
    CREATE_EMPLOYEE,
    CREATE_EMPLOYEE_SUCCESS,
    CREATE_EMPLOYEE_FAILURE,
    UPDATE_EMPLOYEE,
    UPDATE_EMPLOYEE_SUCCESS,
    UPDATE_EMPLOYEE_FAILURE,
    DELETE_EMPLOYEE,
    DELETE_EMPLOYEE_SUCCESS,
    DELETE_EMPLOYEE_FAILURE,
} from '../actions/employeeActions';

const API_URL = 'http://localhost:2001/api/employees';

function* fetchEmployees() {
    try {
        const response = yield call(axios.get, API_URL);
        yield put({ type: GET_EMPLOYEES_SUCCESS, payload: response.data });
    } catch (error) {
        yield put({ type: GET_EMPLOYEES_FAILURE, error });
    }
}

function* createEmployee(action) {
    try {
        const response = yield call(axios.post, API_URL, action.payload);
        yield put({ type: CREATE_EMPLOYEE_SUCCESS, payload: response.data });
    } catch (error) {
        yield put({ type: CREATE_EMPLOYEE_FAILURE, error });
    }
}

function* updateEmployee(action) {
    try {
        const response = yield call(axios.put, `${API_URL}/${action.payload.id}`, action.payload.employee);
        yield put({ type: UPDATE_EMPLOYEE_SUCCESS, payload: response.data });
    } catch (error) {
        yield put({ type: UPDATE_EMPLOYEE_FAILURE, error });
    }
}

function* deleteEmployee(action) {
    try {
        yield call(axios.delete, `${API_URL}/${action.payload}`);
        yield put({ type: DELETE_EMPLOYEE_SUCCESS, payload: action.payload });
    } catch (error) {
        yield put({ type: DELETE_EMPLOYEE_FAILURE, error });
    }
}

export default function* employeeSaga() {
    yield takeEvery(GET_EMPLOYEES, fetchEmployees);
    yield takeEvery(CREATE_EMPLOYEE, createEmployee);
    yield takeEvery(UPDATE_EMPLOYEE, updateEmployee);
    yield takeEvery(DELETE_EMPLOYEE, deleteEmployee);
}
