import React from "react";
import { Wine } from "types/Wine";
import './Modal.scss';

type Props = {
  selectedWine: Wine | null;
  setSelectedWine: (us: Wine | null) => void;
  add: (wine: Wine) => void;
};

export const Modal: React.FC<Props> = ({
  selectedWine,
  setSelectedWine,
  add,
}) => {
  if (selectedWine === null) {
    return null;
  }

  const handleAddToCart = () => {
    if (selectedWine) {
      add(selectedWine); 
    }
  };

  const handleClick = () => {
    setSelectedWine(null); 
  };

  return (
    <div className={`modal ${selectedWine ? "modal_active" : ""}`}>
      <div className="modal-background" onClick={handleClick}></div>
      <div className={`modal_content ${selectedWine ? "modal_content_active" : ""}`}>
        <button className="modal-close" onClick={handleClick}>×</button>
        <div style={{ display: "flex" }}>
          <img src={selectedWine.image_url} alt="wine-img" />
          <div className="modal_text_content">
            <h2 className="modal_title">{selectedWine.name}</h2>
            <p className="modal_description">{selectedWine.description}</p>
          </div>
        </div>
        <button className="modal_add" onClick={handleAddToCart}>Add to Cart</button>
      </div>
    </div>
  );
};
