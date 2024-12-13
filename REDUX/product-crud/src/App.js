import React, { useState } from 'react';
import ProductList from './components/ProductList';
import ProductForm from './components/ProductForm';

const App = () => {
  const [editProduct, setEditProduct] = useState(null);

  return (
    <div className="App">
      <h1>Product Management</h1>
      <ProductForm editProduct={editProduct} setEditProduct={setEditProduct} />
      <ProductList setEditProduct={setEditProduct} />
    </div>
  );
};

export default App;
