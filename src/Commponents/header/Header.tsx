import React from "react";
import './Header.scss';
import { User } from "types/User";

type Props = {
  user: User | undefined;
}

export const Header: React.FC<Props> = ({ user }) => {
  return (
    <div className="header">
      <h1 className="title">Wine Library</h1>

      <div className="user">
            <img 
              className="user-photo" 
              src="" 
              alt="" 
            />
            <span className="user-name">{user?.first_name}</span>
            <p className="user-status">{user?.last_name}</p>
      </div>
    </div>
  )
}