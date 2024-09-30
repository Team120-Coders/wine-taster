import React from "react";
import "./WineList.scss";
import { WineItem } from "Commponents/WineItem/WineItem";
import { Wine } from "types/Wine";

type Props = {
  wines: Wine[];
  setSelectedWine: (us: Wine | null) => void;
}

export const WineList: React.FC<Props> = ({ wines, setSelectedWine }) => {
  return (
    <div className="items-list">
      {wines.map(wine => (
      <WineItem key={wine.id} wine={wine} setSelectedWine={setSelectedWine} />
      ))}
    </div>
  )
}