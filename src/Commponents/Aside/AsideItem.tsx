import React from "react";
import "./AsideItem.scss";
import { Wine } from "types/Wine";

type Props = {
  wine: Wine;
}

export const AsideItem:React.FC<Props> = ({ wine }) => {
  return (
    <><div className="cart-item">
        <img className="wine-img" src={wine.image_url} alt="wine" />

        <div className="item-info">
          <p className="item-title">{wine.name}</p>
          <p className="item-description">{wine.description}</p>
          <span className="item-price">{wine.price}</span>
        </div>
      </div></>
  )
}