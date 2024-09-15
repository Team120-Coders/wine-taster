import React from "react";
import "./WineFilter.scss"

type Props = {
  filter: string;
  setFilter: (filter: string) => void;
  search: string;
  setSearch: (search: string) => void;
}

export const WineFilter: React.FC<Props> = ({
  filter,
  setFilter,
  search,
  setSearch,
}) => {

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value.toLowerCase());
  };

  return (
    <>
    <form className="filter-form">
      <input 
        type="text"
        className="input"
        placeholder="Search..."
        value={search}
        onChange={handleSearchChange}
      />
      <button className="input-button" onClick={() => setSearch('')}>X</button>

    </form>
    <div className="filter-types">
      <button className="filter-button" onClick={() => setFilter('All')}>All</button>
      <button className="filter-button" onClick={() => setFilter('Red')}>Red</button>
      <button className="filter-button" onClick={() => setFilter('White')}>White</button>
      <button className="filter-button" onClick={() => setFilter('Rose')}>Rose</button>
      <button className="filter-button" onClick={() => setFilter('Sparkling')}>Sparkling</button>
    </div>
    </>
  )
}