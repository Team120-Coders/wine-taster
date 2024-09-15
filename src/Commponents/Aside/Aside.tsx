import React from "react";
import "./Aside.scss";
import { AsideItem } from "./AsideItem";

export const Aside: React.FC = () => {
  return (

    <><div className="aside">
        <div className="cart-header">
          <h2>Cart</h2>
          <button className="clear-button">Clear</button>
        </div>
      <AsideItem /> 
      <AsideItem />
      <AsideItem />
      <div className="checkout">
        <p>Subtotal: $96</p>
        <p>Tax: $2</p>
        <p>Discount: $0</p>
        <h3>Total: $98</h3>
        <button>Charge customer</button>
      </div>
    </div></>
  )
}