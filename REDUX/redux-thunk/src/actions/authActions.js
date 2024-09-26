// src/actions/authActions.js
import axios from 'axios';

export const loginUser = (credentials) => async (dispatch) => {
  try {
    const res = await axios.post('http://localhost:2001/auth/login', credentials);
    localStorage.setItem('token', res.data);
    dispatch({ type: 'LOGIN_SUCCESS', payload: res.data });
  } catch (error) {
    dispatch({ type: 'LOGIN_FAIL', payload: error.response.data });
  }
};

export const registerUser = (userData) => async (dispatch) => {
  try {
    await axios.post('http://localhost:2001/auth/signup', userData);
    dispatch({ type: 'REGISTER_SUCCESS' });
  } catch (error) {
    dispatch({ type: 'REGISTER_FAIL', payload: error.response.data });
  }
};

export const logoutUser = () => {
  localStorage.removeItem('token');
  return { type: 'LOGOUT' };
};

export const checkTokenExpiration = () => (dispatch) => {
  const token = localStorage.getItem('token');
  if (token) {
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    const expirationTime = decodedToken.exp * 1000;

    if (Date.now() >= expirationTime) {
      dispatch(logoutUser());
      dispatch({ type: 'TOKEN_EXPIRED' });
    }
  }
};
