import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown'; // Optional, for dropdowns
import Container from 'react-bootstrap/Container'; // Optional, for layout
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import Home from './layouts/Home';
import About from './layouts/About';
import ContactUs from './layouts/ContactUs';
import { Button, Modal } from 'react-bootstrap';
import { useState, useEffect } from 'react';
import Login from './layouts/Login';
import Signup from './layouts/Signup';
import axios from 'axios';
import { baseURL } from './constants/Constants';
const NavBarCmp = () => {
    const [showSignup, setShowSignup] = useState(false);
    const [showLogin, setShowLogin] = useState(false);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const updateIsAuthenticated = () => {
        setIsAuthenticated(true);
    }
    const handleLogout = () => {
        alert('logging out');
        axios.post(baseURL+'/logout',
            {},
            {
                withCredentials: true
            }
        )
        .then(response => {
            console.log('logout res:',response);
            setIsAuthenticated(false);
        })
        .catch(error => {
            console.log('logout err:',error);
        })
    }

    const getTodoRecs = () => {
        if(isAuthenticated) {
            console.log('get todo recs');
            axios.get(baseURL+'/todo/get-todos',
                {withCredentials: true}
            )
            .then(response => {
                console.log('todos:',response.data);
            })
        } else {
            alert('please login first');
        }
    }

    

    useEffect(() => {
        axios.get(baseURL+'/api/auth/check',
            {
                withCredentials: true
            }
        )
        .then(res => {
            console.log('res:',res);
            if(res.data.authenticated) {
                setIsAuthenticated(res.data.authenticated);
            } else {
            }
        })
        .catch(error => {
            console.log(error.message);
        })
    });

    return (
        <>
            <Router>
                <Navbar bg='light' data-bs-them='dark' expand='lg'>
                    <Container>
                        <Navbar.Brand href='/home'>NavBar</Navbar.Brand>
                        <Nav>
                            <Nav.Link href='/home'>Home</Nav.Link>
                            <Nav.Link onClick={getTodoRecs}>Get Todo</Nav.Link>
                            <Nav.Link href='/about'>About</Nav.Link>
                            <Nav.Link href='/contactus'>Contact Us</Nav.Link>
                            {!isAuthenticated ? (
                                <>
                                    <Nav.Link onClick={() => setShowLogin(true)}>Login</Nav.Link>
                                    <Nav.Link onClick={() => setShowSignup(true)}>Sign Up</Nav.Link>
                                </>
                            ) : (
                                <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
                            )}
                        </Nav>
                    </Container>
                </Navbar>
                <Routes>
                    <Route path='/' element={<Home />}/>
                    <Route path='/home' element={<Home />}/>
                    <Route path='/about' element={<About />}/>
                    <Route path='/contactus' element={<ContactUs />}/>
                </Routes>
            </Router>
            <Login show={showLogin} hide={() => setShowLogin(false)} isAuthenticated={updateIsAuthenticated}/>
            <Signup show={showSignup} hide={() => setShowSignup(false)} isAuthenticated={updateIsAuthenticated}/>
        </>
    )
}

export default NavBarCmp;