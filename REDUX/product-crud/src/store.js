import { configureStore } from '@reduxjs/toolkit';
import productReducer from '../src/Products/ProductSlice';

export const store = configureStore({
  reducer: {
    products: productReducer,
  },
});
