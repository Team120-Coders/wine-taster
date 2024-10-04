import React from "react";
import './Header.scss';
import { NavLink } from "react-router-dom";

export const Header: React.FC = () => {
  return (
    <div className="header">
      <h1 className="title">Wine Library</h1>

      <div className="user">
        <NavLink className="user-link" to="/login">
            <img 
              className="user-photo" 
              src="" 
              alt="" 
            />
            <span className="user-name">Parnel Horn</span>
            <p className="user-status">Emplayee</p>
        </NavLink>
      </div>
    </div>
  )
}