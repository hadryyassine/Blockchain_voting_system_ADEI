import './App.css';
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import SignupPage from './Pages/Signup';
import LoginPage from './Pages/Login';
import ElectionPage from './Pages/Electionform';
import Table from './components/CandidateTable';
import ElectionUpdateForm from './Pages/ElectionUpdateForm';

function App() {
  return (
    // <div className="min-h-full h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    // <div className="max-w-md w-full space-y-8">
     <BrowserRouter>
        <Routes>  
            <Route path="/" element={<LoginPage/>} />
            <Route path="/signup" element={<SignupPage/>} />
            <Route path ="/election" element={<ElectionPage/>}/>
            <Route path = "/candidates" element={<Table/>}/>
            <Route path="/candidates/:id" element = {<ElectionUpdateForm/>}/>
        </Routes>
      </BrowserRouter>
  //    </div>
  //  </div>
  );
}

export default App;