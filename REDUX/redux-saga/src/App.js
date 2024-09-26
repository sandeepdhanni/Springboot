import React from "react";
import { Provider } from "react-redux";
import store from "./redux/store";
import Login from "./pages/Login";
import Register from "./pages/Register";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import { Route, Routes } from "react-router-dom";
import { BrowserRouter } from "react-router-dom";
import Navbar from "./pages/Navbar";

const App = () => {
  return (
    <Provider store={store}>
      <BrowserRouter>
      <Navbar />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/dashboard" element={<EmployeeDashboard />} />
        </Routes>
      </BrowserRouter>
    </Provider>
  );
};

export default App;
