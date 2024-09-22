import { call, put, takeLatest } from 'redux-saga/effects';
import { loginUserSuccess, loginUserFailure } from '../actions/authActions';

// Simulated API login function
function loginApi(credentials) {
  // Simulate an API request here, using credentials
  if (credentials.email === 'test@example.com' && credentials.password === 'password') {
    return { token: 'fake-token', user: { email: 'test@example.com' } };
  } else {
    throw new Error('Invalid credentials');
  }
}

// Worker saga: handles login API call
function* handleLogin(action) {
  try {
    const response = yield call(loginApi, action.payload); // Add yield here to handle side effect
    yield put(loginUserSuccess(response)); // Dispatch success action with response
  } catch (error) {
    yield put(loginUserFailure(error.message)); // Dispatch failure action with error message
  }
}

// Watcher saga: listens for LOGIN_REQUEST action
function* authSagas() {
  yield takeLatest('LOGIN_REQUEST', handleLogin); // Add yield here
}

export default authSagas;
