import React from 'react'

const Footer = () => {
  return (
   <>
   <footer className='footer'>
   <div className="footer-container">
        
        <div className="footer-section">
          <h3 className="footer-heading">Digital Services</h3>
          <nav className="footer-nav">
            <ul>
              <li><a href="/">Cloud Engineering</a></li>
              <li><a href="/">Data, Analytics & AI</a></li>
              <li><a href="/">Intelligent Automation</a></li>
              <li><a href="/">QA and Automation</a></li>
              <li><a href="/">Service & UX Design</a></li>
              <li><a href="/">Enterprise Agility</a></li>
              <li><a href="/">Application Management</a></li>
            </ul>
          </nav>
        </div>
        <div className="footer-section">
          <h3 className="footer-heading">Industries</h3>
          <nav className="footer-nav">
            <ul>
              <li><a href="/">Banking & Finance</a></li>
              <li><a href="/">Healthcare</a></li>
              <li><a href="/">Government</a></li>
              <li><a href="/">Education</a></li>
              <li><a href="/">Manufacturing</a></li>
            </ul>
          </nav>
        </div>
        <div className="footer-section">
          <h3 className="footer-heading">Company</h3>
          <nav className="footer-nav">
            <ul>
              <li><a href="/">Clients</a></li>
              <li><a href="/">Careers</a></li>
              <li><a href="/">Contact</a></li>
            </ul>
          </nav>
        </div>
        <div className="footer-section">
          <h3 className="footer-heading">Social</h3>
          <nav className="footer-nav">
            <ul>
              <li><a href="/">Linkedin</a></li>
              <li><a href="/">Facebook</a></li>
              <li><a href="/">Instagram</a></li>
            </ul>
          </nav>
        </div>
      </div>
   </footer>
   </>
  )
}

export default Footer