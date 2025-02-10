import React from 'react'
import Navbar from './Navbar'
import Footer from './Footer'

const Base = ({ Title = "Welcome to Document Proccess Application", children }) => {
    return (
       
            <div className='base'>
                <div>
                    <Navbar />
                </div>
                <div className='base-children'>
                    {children}
                </div>
                <div>
                    <Footer />
                </div>

            </div>
      
    )
}

export default Base