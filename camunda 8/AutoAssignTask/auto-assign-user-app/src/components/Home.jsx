import React from 'react';

import Base from './Base';

const Home = () => {
const handleOption=(event)=>{
console.log("option value : ",event.target.value);

}
const handleSearch=(event)=>{
  console.log("search value : ",event.target.value);
}
  return (
    <Base>
      <div className='home'>
        <select onChange={handleOption}>
          <option>
            All
          </option>
          <option>
            abc
          </option>
          <option>
            xyz
          </option>
        </select>
        <input type='text' placeholder='search' onChange={handleSearch} />
      </div>
    </Base>
  );
};

export default Home;
