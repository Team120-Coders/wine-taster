import React from "react";
import "./AsideItem.scss";

export const AsideItem:React.FC = () => {
  return (
    <><div className="cart-item">
        <img className="wine-img" src="/imges/wine4.png" alt="wine" />

        <div className="item-info">
          <p className="item-title">Casa Castillo Monastrell</p>
          <p className="item-description">Bottle size: 750 ml</p>
          <span className="item-price">$20</span>
        </div>

        <div className="quantity">
          <button className="aside-button">-</button>
          <span>2</span>
          <button className="aside-button">+</button>
        </div>
      </div></>
  )
}