import { put, takeEvery } from 'redux-saga/effects';

function* fetchEmployeesSaga() {
    // Simulating an API call
    const employees = [
        { id: 1, name: 'John Doe', role: 'Developer' },
        { id: 2, name: 'Jane Smith', role: 'Manager' },
    ];
    yield put({ type: 'FETCH_EMPLOYEES_SUCCESS', payload: employees });
}

function* employeeSagas() {
    yield takeEvery('FETCH_EMPLOYEES_REQUEST', fetchEmployeesSaga);
}

export default employeeSagas;