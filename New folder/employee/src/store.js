import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import authReducer from './redux/reducers/authReducer'; // Adjust the path if needed
import employeeReducer from './redux/reducers/employeeReducer'; // Adjust the path if needed
import rootSaga from './redux/sagas'; // Adjust the path if needed

// Create saga middleware
const sagaMiddleware = createSagaMiddleware();

// Configure the store
const store = configureStore({
  reducer: {
    auth: authReducer,
    employees: employeeReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ thunk: true }).concat(sagaMiddleware),
});

// Run the root saga
sagaMiddleware.run(rootSaga);

export default store;
