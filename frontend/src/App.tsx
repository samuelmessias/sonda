import 'react-toastify/dist/ReactToastify.css';
import './assets/styles/custom.scss';
import './App.css';

import { ToastContainer } from 'react-toastify';
import Navbar from './components/Navbar';
import Home from 'components/Home';

function App() {
  return (
    <>
      <ToastContainer />
      <Navbar />
      <Home />
    </>
  );
}

export default App;
