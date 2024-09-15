import './App.css';
import {
  HashRouter,
  Navigate,
  Route,
  Routes,
} from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import { Header } from './Commponents/header/Header';
import { WineFilter } from './Commponents/WineFilter';
import { Aside } from 'Commponents/Aside/Aside';
import { WineList } from 'Commponents/WineList/WineList';
import { Modal } from 'Commponents/Modal/Modal';
import { Wine } from 'types/Wine'; 
import { getWines } from 'api/wines';
import { authClient } from 'api/authClient';
import axios from 'axios';
import { User } from 'types/User';
import { Profiel } from 'Profiel/Profiel';
import { Login } from 'Commponents/Login/Login';

export const App: React.FC = () => {
  const [wines, setWines] = useState<Wine[]>([]); 
  const [loader, setLoader] = useState(false);
  const [filter, setFilter] = useState('All');
  const [search, setSearch] = useState('');
  const [token, setToken] = useState<string | null>(null); 
  const [users, setUsers] = useState<User[]>([]);
  const [email, setEmail] = useState('');
  const [psw, setPsw] = useState('');

  const handleAuth = async () => {
    try {
      const response = await authClient.login(email, psw);
      setToken(response.token);
    } catch (err) {
      console.error('Ошибка авторизации', err);
    }
  };

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();

    const fetchWines = async () => {
      if (!token) return;

      try {
        setLoader(true);
        const response: Wine[] = await getWines(token);
        if (isMounted) {
          setWines(response);
        }
      } catch (err) {
        console.error('Ошибка при загрузке вин', err);
      } finally {
        setLoader(false);
      }
    };

    handleAuth().then(fetchWines);

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, [token]); 

  const login = async (email: string, password: string) => {
    try {
      const response = await axios.post('https://wine-taster-app-production.up.railway.app/api/login', {
        email,
        password
      });
      console.log('Login successful', response.data);
      return response.data;  // Например, токен авторизации
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error('Ошибка авторизации', error.message);
      } else {
        console.error('Произошла ошибка', error);
      }
    }
  };

  const handleWineFilter = wines
    .filter((wine) => {
      switch (filter) {
        case 'Red':
          return wine.grape_variety === 'Red';
        case 'Sparkling':
          return wine.grape_variety === 'Sparkling';
        case 'White':
          return wine.grape_variety === 'White';
        case 'Rose':
          return wine.grape_variety === 'Rose';
        default:
          return true;
      }
    })
    .filter((wine) => wine.name.toLowerCase().includes(search.toLowerCase()));

  return (
    <HashRouter > 
    <Routes>
      <Route path='/' element={<Login />} />
      <Route path='/profiel' element={<Profiel />} />
      <Route path="/www" element={<>
      <div className='content'>
        <div className='main-content'>
          <Header />

          {loader ? (
            <p>Loading wines...</p>
          ) : (
            <>
              <WineFilter filter={filter} setFilter={setFilter} search={search} setSearch={setSearch} />
              <WineList wines={handleWineFilter} />
            </>
          )}
        </div>

        <Aside />
      </div>
      <Modal />
    </>} />
    </Routes>
    </HashRouter>
  );
};
