// import React from 'react';
// import { Link } from 'react-router-dom';
// import '../styles/styles.css';

// const Navbar = () => {
//   return (
//     <nav className="navbar">
//       <h1 className="navbar-brand">Employee Management System</h1>
//       <div className="navbar-links">
//         <Link to="/" className="nav-link">Login</Link>
//         <Link to="/register" className="nav-link">Register</Link>
//       </div>
//     </nav>
//   );
// };

// export default Navbar;
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/styles.css';

const Navbar = () => {
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  // Handle logout
  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <h1 className="navbar-brand">Employee Management System</h1>
      <div className="navbar-links">
        {/* Conditionally render Login/Register or Logout based on the token */}
        {!token ? (
          <>
            <Link to="/login" className="nav-link">Login</Link>
            <Link to="/register" className="nav-link">Register</Link>
          </>
        ) : (
          <button onClick={handleLogout} className="nav-link logout-btn">
            Logout
          </button>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
