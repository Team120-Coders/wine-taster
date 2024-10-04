import React from "react";
import "./Aside.scss";
import { Wine } from "types/Wine";
import { AsideItem } from "./AsideItem";

type Props = {
  items: Wine[];
  setCardItems: ([]) => void;
};

export const Aside: React.FC<Props> = ({ items, setCardItems }) => {
  const handelClear = () => {
    setCardItems([]);
  };

  return (
    <div className="aside">
      <div className="cart-header">
        <h2 className="cart_title">Cart</h2>
        <button className="clear-button" onClick={handelClear}>Clear</button>
      </div>
      {items.length === 0 ? (
        <p>Here you can add wine that you like</p>
      ) : (
        items.map((wine, index) => (
          <AsideItem key={index} wine={wine} />
        ))
      )}
    </div>
  );
};
