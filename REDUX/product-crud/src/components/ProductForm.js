import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { addProduct, updateProduct } from '../Products/ProductSlice';

const ProductForm = ({ editProduct, setEditProduct }) => {
  const [title, setTitle] = useState('');
  const dispatch = useDispatch();

  useEffect(() => {
    if (editProduct) {
      setTitle(editProduct.title);
    }
  }, [editProduct]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (editProduct) {
      dispatch(updateProduct({ ...editProduct, title }));
      setEditProduct(null);
    } else {
      dispatch(addProduct({ title }));
    }
    setTitle('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{editProduct ? 'Edit Product' : 'Add Product'}</h2>
      <input
        type="text"
        placeholder="Product title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <button type="submit">{editProduct ? 'Update' : 'Add'}</button>
      {editProduct && <button onClick={() => setEditProduct(null)}>Cancel</button>}
    </form>
  );
};

export default ProductForm;
