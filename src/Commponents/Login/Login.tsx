import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser, registerUser } from 'api/authClient';
import './Login.scss';

export const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [isLoginMode, setIsLoginMode] = useState(true);

  const [login, setLogin] = useState('');
  const navigate = useNavigate();
  const eml = localStorage.setItem('mail',email);
  const psw = localStorage.setItem('pass',password);

  const handleLogin = async () => {
    try {
      const { token } = await loginUser( email, password );
      localStorage.setItem('jwtToken', token);
      navigate('/www');
    } catch (error) {
      console.error('Ошибка при входе:', error);
    }
  };

  const handleRegister = async () => {
    try {
      const userData = {
        email,
        login,
        password,
        repeatPassword: password,
        firstName,
        lastName,
      };
  
      await registerUser(userData);
      await loginUser(
        email,
        password,
      );
      localStorage.setItem('mail', email);
      localStorage.setItem('psw', password);
      navigate('/www');
    } catch (error) {
      console.error('Ошибка при регистрации:', error);
    }
  };



  return (
    <div className="auth-container">
      <div className="auth-header">
        <button 
          className={`toggle-button ${isLoginMode ? 'active' : ''}`}
          onClick={() => setIsLoginMode(true)}
        >
          Log In
        </button>
        <button 
          className={`toggle-button ${!isLoginMode ? 'active' : ''}`}
          onClick={() => setIsLoginMode(false)}
        >
          Register
        </button>
      </div>
      
      {isLoginMode ? (
        <form onSubmit={(e) => { e.preventDefault(); handleLogin(); }}>
          <input
            type="email"
            placeholder="User Id"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="submit-button" type="submit">Log In</button>
        </form>
      ) : (
        <form onSubmit={(e) => { e.preventDefault(); handleRegister(); }}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="text"
            placeholder="Login"
            value={login}
            onChange={(e) => setLogin(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <input
            type="text"
            placeholder="First Name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
          <input
            type="text"
            placeholder="Last Name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
          <button className="submit-button" type="submit">Register</button>
        </form>
      )}
    </div>
  );
};