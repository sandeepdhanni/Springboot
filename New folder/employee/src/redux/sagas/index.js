import { all } from 'redux-saga/effects';
import authSagas from './authSagas'; // Corrected the typo
import employeeSagas from './employeeSagas'; // Ensure you have employeeSagas.js

export default function* rootSaga() {
  yield all([
    authSagas(),
    employeeSagas(),
  ]);
}
