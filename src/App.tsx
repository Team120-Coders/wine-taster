import './App.css';
import {
  HashRouter,
  Route,
  Routes,
} from 'react-router-dom';
import React, { useCallback, useEffect, useState } from 'react';
import { Header } from './Commponents/header/Header';
import { WineFilter } from './Commponents/WineFilter';
import { Aside } from 'Commponents/Aside/Aside';
import { WineList } from 'Commponents/WineList/WineList';
import { Modal } from 'Commponents/Modal/Modal';
import { Wine } from 'types/Wine'; 
import { Login } from 'Commponents/Login/Login';
import { getUserProfile, getWines } from 'api/authClient';
import { User } from 'types/User';

export const App: React.FC = () => {
  const [wines, setWines] = useState<Wine[]>([]); 
  const [user, setUser] = useState<User>();
  const [loader, setLoader] = useState(false);
  const [filter, setFilter] = useState('All');
  const [search, setSearch] = useState('');
  const [selectedWine, setSelectedWine] = useState<Wine | null>(null);
  const [cardItems, setCardItems] = useState<Wine[]>([]);
  const email = localStorage.getItem('mail');
  const password = localStorage.getItem('pass');

  
  useEffect(() => {
    const storedCardItems = localStorage.getItem('cardItems');
    if (storedCardItems) {
      setCardItems(JSON.parse(storedCardItems));
    }
  }, []);

  useEffect(() => {

    setLoader(true);
    getUserProfile(email, password)
    .then(setUser)
    .finally(() => setLoader(false));
  }, [email, password])

  useEffect(() => {
    setLoader(true);

    setTimeout(() => {
      localStorage.setItem('cardItems', JSON.stringify(cardItems));
      setLoader(false);
    }, 100);
  }, [cardItems]);

  useEffect(() => {
    setLoader(true);

    setTimeout(() => {
      getWines(email, password)
        .then(setWines)
        .finally(() => setLoader(false));
    }, 100);
  }, [email, password]);
  
  console.log(localStorage);
  console.log(wines);

  const addToCard =  useCallback((wine: Wine) => {
    setCardItems((previous) => {
      const wineExists = previous.some(item => item.id === wine.id);
      if (!wineExists) {
        return [...previous, wine];
      } 
      
      return previous;
    });
  }, []);

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
    <HashRouter> 
      <Routes>
        <Route 
          path='/' 
          element={<Login />}
        />
        <Route path="/www" element={<>
          <div className='content'>
            <div className='main-content'>
              <Header user={user} />
              {loader ? (
                <p>Loading wines...</p>
              ) : (
                <>
                  <WineFilter filter={filter} setFilter={setFilter} search={search} setSearch={setSearch} />
                  <WineList wines={handleWineFilter} setSelectedWine={setSelectedWine} />
                </>
              )}
            </div>
            <Aside items={cardItems} setCardItems={setCardItems}/>
          </div>
          {selectedWine ? <Modal selectedWine={selectedWine} setSelectedWine={setSelectedWine} add={addToCard}/> : ''}
        </>} />
      </Routes>
    </HashRouter>
  );
};
