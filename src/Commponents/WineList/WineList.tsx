import React from "react";
import "./WineList.scss";
import { WineItem } from "Commponents/WineItem/WineItem";
import { Wine } from "types/Wine";

type Props = {
  wines: Wine[];
}

export const WineList: React.FC<Props> = ({ wines }) => {
  return (
    <div className="items-list">
      {wines.map(wine => (
      <WineItem key={wine.id} wine={wine} />
      ))}
    </div>
  )
}