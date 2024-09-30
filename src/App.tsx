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
import { Profiel } from 'Profiel/Profiel';
import { Login } from 'Commponents/Login/Login';
import { getWines } from 'api/authClient';

export const App: React.FC = () => {
  const [wines, setWines] = useState<Wine[]>([]); 
  const [loader, setLoader] = useState(false);
  const [filter, setFilter] = useState('All');
  const [search, setSearch] = useState('');
  const [selectedWine, setSelectedWine] = useState<Wine | null>(null);
  const [cardItems, setCardItems] = useState<Wine[]>([]);
  const token = localStorage.getItem('jwtToken');
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
  }, [email, password, token]);
  
  console.log(token);
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
