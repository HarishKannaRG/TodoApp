import React, { useState } from 'react';
import axios from "axios";
import { baseURL } from "../constants/Constants";
import { useNavigate } from 'react-router-dom';
const Signup = ({isAuthenticated}) => {
    const [name, setName] = useState('');
    const [username, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    axios.defaults.baseURL=baseURL;
    axios.defaults.withCredentials=true;
    const handleSignup = (e) => {
        console.log('handleSignup called');
        e.preventDefault();
        axios.post(
            baseURL+'/users/create-user',
            {
                email:username,
                password,
                name
            },
            {
                headers: {
                    'Content-Type':'application/json'
                },
                withCredentials: true
            }
        )
        .then(res => {
            alert("sign up success");
            const formData = new URLSearchParams();
            formData.append('username', username);
            formData.append('password', password);
            axios.post(
                baseURL + '/login',
                formData,
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    withCredentials: true
                }
            )
            .then(res => {
                console.log('res from signup login:',res);
                isAuthenticated(true);
                navigate('/home', { replace: true });
            })
            .catch(err => {
                console.log('err in signup login', err);
            })
        })
        .catch(e => {
            const serverMessage =
                e.response && e.response.data && e.response.data.error
                    ? e.response.data.error
                    : e.message;
            console.log('new e:', JSON.stringify(e));
            console.log('e response:', e.response);
            setError(serverMessage);
        })
    }
    // return (
    //     <Modal show={show} onHide={hide}>
    //         <Modal.Header closeButton>
    //             <Modal.Title>Sign Up</Modal.Title>
    //         </Modal.Header>
    //         <Modal.Body>
    //             <form onSubmit={handleSignup}>
    //                 <input type="text" placeholder="Name" value={name} onChange={e => setName(e.target.value)}></input>
    //                 <input type="text" placeholder="Username" value={username} onChange={e => setUserName(e.target.value)}></input>
    //                 <input type="text" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)}></input>
    //             </form>
    //         </Modal.Body>
    //         <Modal.Footer>
    //             <Button variant="secondary" onClick={hide}>Close</Button>
    //             <Button variant="primary" onClick={handleSignup}>Sign Up</Button>
    //         </Modal.Footer>
    //     </Modal>
    // );
    return (
    	<div className="login-page">
			<form className="login-form" onSubmit={handleSignup}>
				<h2 className="login-title">Sign Up</h2>
				{error && <div className="login-error">{error}</div>}
                <div className="login-field">
					<label htmlFor="username">Name</label>
					<input
						id="name"
						type="text"
						value={name}
						onChange={e => setName(e.target.value)}
						autoComplete="name"
						required
						placeholder="Enter your name"
					/>
				</div>
				<div className="login-field">
					<label htmlFor="username">Username</label>
					<input
						id="username"
						type="text"
						value={username}
						onChange={e => setUserName(e.target.value)}
						autoComplete="username"
						required
						placeholder="Enter your username"
					/>
				</div>
				<div className="login-field">
					<label htmlFor="password">Password</label>
					<input
						id="password"
						type="password"
						value={password}
						onChange={e => setPassword(e.target.value)}
						autoComplete="current-password"
						required
						placeholder="Enter your password"
					/>
				</div>
				<button className="login-btn" type="submit">
					Sign Up
				</button>
				<a href="/login">Already have an account? Login</a>
			</form>
		</div>
	);
}

export default Signup;