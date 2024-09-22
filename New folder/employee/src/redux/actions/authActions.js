export const loginUser = (credentials) => ({
    type: 'LOGIN_REQUEST',
    payload: credentials,
  });
  
  export const loginUserSuccess = (data) => ({
    type: 'LOGIN_SUCCESS',
    payload: data,
  });
  
  export const loginUserFailure = (error) => ({
    type: 'LOGIN_FAILURE',
    payload: error,
  });
  
  export const registerUser = (credentials) => ({
    type: 'REGISTER_REQUEST',
    payload: credentials,
  });
  
  export const registerUserSuccess = (data) => ({
    type: 'REGISTER_SUCCESS',
    payload: data,
  });
  
  export const registerUserFailure = (error) => ({
    type: 'REGISTER_FAILURE',
    payload: error,
  });
  