import React from "react";
import "./WineItem.scss";
import { Wine } from "types/Wine";

type Props = {
  wine: Wine;
  setSelectedWine: (wine: Wine | null) => void;
}

export const WineItem: React.FC<Props> = ({ wine, setSelectedWine }) => {

  const handleClick = () => {
    setSelectedWine(wine); // Сначала устанавливаем выбранное вино
  };

  return (
    <div className="wine" onClick={handleClick}>
      <img className="img" src="/imges/wine4.png" alt={wine.name} />

      <div className="item-wrapper">
        <p className="wine-name">{wine.name}</p>
        <p className="wine-description">Country: {wine.region} • Bottle size: {wine.size} ml</p>
        <span className="price_title">Approximate price:</span>
        <p className="price">${wine.price}</p>
      </div>
    </div>
  );
}
