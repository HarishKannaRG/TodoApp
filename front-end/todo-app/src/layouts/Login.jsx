import { Button, Modal } from "react-bootstrap";
import React, { useState } from 'react';
import axios from "axios";
import { baseURL } from "../constants/Constants";
import '../style/Login.css';
import { useNavigate } from 'react-router-dom';
const Login = ({ isAuthenticated }) => {
	const [username, setUserName] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const navigate = useNavigate();
	axios.defaults.baseURL = baseURL;
	axios.defaults.withCredentials = true;
	const handleLogin = async (e) => {
		console.log('handle login called');
		e.preventDefault();
		const formData = new URLSearchParams();
		formData.append('username', username);
		formData.append('password', password);
		try {
			const response = await axios.post(
				baseURL + '/login',
				formData,
				{
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					withCredentials: true
				}
			);
			console.log('response', response);
			if (response.status == 200) {
				alert("login successful");
				isAuthenticated(true);
				navigate('/home', { replace: true });
			} else {
				alert("login failed");
			}
		} catch (e) {
			const serverMessage =
                e.response && e.response.data && e.response.data.error
                    ? e.response.data.error
                    : e.message;
            console.log('new e:', JSON.stringify(e));
            console.log('e response:', e.response);
			console.log('error message:', serverMessage)
			setError(serverMessage)
			// alert("login failed in catch");
		}

	}
	// return (
	//     <Modal show={show} onHide={hide}>
	//          <Modal.Header closeButton>
	//             <Modal.Title>Login</Modal.Title>
	//         </Modal.Header>
	//         <Modal.Body>
	//             <form onSubmit={handleLogin}>
	//                 <input type="text" placeholder="Username" value={username} onChange={e => setUserName(e.target.value)}></input>
	//                 <input type="text" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)}></input>
	//             </form>
	//         </Modal.Body>
	//         <Modal.Footer>
	//             <Button variant="secondary" onClick={hide}>Close</Button>
	//             <Button variant="primary" onClick={handleLogin}>Login</Button>
	//         </Modal.Footer>
	//     </Modal>
	// );
	return (
		<div className="login-page">
			<form className="login-form" onSubmit={handleLogin}>
				<h2 className="login-title">Log In</h2>
				{error && <div className="login-error">{error}</div>}
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
					Login
				</button>
				<a href="/signup">Create an account</a>
			</form>
		</div>
	);
}

export default Login;