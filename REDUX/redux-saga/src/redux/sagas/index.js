import { all } from 'redux-saga/effects';
import employeeSaga from './employeeSagas';

export default function* rootSaga() {
    yield all([employeeSaga()]);
}
