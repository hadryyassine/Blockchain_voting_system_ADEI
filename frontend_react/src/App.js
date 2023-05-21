import './App.css';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import SignupPage from './Pages/Signup';
import LoginPage from './Pages/Login';
import DashboardPage from './Pages/Dashboard/Dashboard';

function App() {
  return (

     <BrowserRouter>
        <Routes>
            <Route path="/" element={<LoginPage/>} />
            <Route path="/signup" element={<SignupPage/>} />
            <Route path="/dashboard" element={<DashboardPage/>} />
        </Routes>
      </BrowserRouter>
  );
}

export default App;