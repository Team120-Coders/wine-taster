import React from "react";
import "./WineItem.scss";
import { Wine } from "types/Wine";

type Props = {
  wine: Wine;
}

export const WineItem: React.FC<Props> = ({ wine }) => {
  return (
    <div className="wine">
  <img className="img" src="/imges/wine4.png" alt={wine.name} />

  <div className="item-wrapper">
    <p className="wine-name">{wine.name}</p>
    <p className="wine-description">Country: {wine.region} • Bottle size: {wine.size} ml</p>
    <p className="price">${wine.price}</p>
  </div>
</div>
  )
}