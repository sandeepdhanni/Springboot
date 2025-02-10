import React from 'react'
import { Link, NavLink } from 'react-router-dom'

const Navbar = () => {
  return (
    <>
      <div className='navbar-container'>
        <div className="navbar-logo">
          <img
            src="https://eidiko.com/wp-content/uploads/2024/06/eidiko-footer-logo.svg"
            alt="Eidiko Footer Logo"
            
          />
        </div>
        <div className='navbar'>

          <div className='navField'>
            <NavLink>
              <Link to='/'>Home</Link>
            </NavLink>
          </div>

          <div className='navField'>
            <NavLink>
              <Link to='/task'>Manager UI</Link>
            </NavLink>
          </div>

         
          
        </div>
      </div>
    </>
  )
}

export default Navbar;