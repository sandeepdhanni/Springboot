
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './components/Home'
import Task from './components/Task'

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';

function App() {
  return (
    <>
    <BrowserRouter>
    <ToastContainer/>
    <Routes>
    <Route path='/' element={<Home/>}/>
   
    <Route path='/task' element={<Task/>}/>
    </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
