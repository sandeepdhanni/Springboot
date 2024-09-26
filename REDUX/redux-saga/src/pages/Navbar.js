import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/styles.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <h1 className="navbar-brand">Employee Management System</h1>
      <div className="navbar-links">
        <Link to="/" className="nav-link">Login</Link>
        <Link to="/register" className="nav-link">Register</Link>
      </div>
    </nav>
  );
};

export default Navbar;
