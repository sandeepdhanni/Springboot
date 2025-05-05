import React, { useState } from "react";
import "../src/TextBox.css";

export default function TextBox() {
  const [text, setText] = useState("");

  const handleChange = (e) => {
    setText(e.target.value);
    e.target.style.height = "auto"; 
    e.target.style.height = `${e.target.scrollHeight}px`;
  };

  return (
    <div className="textbox-container">
      <label htmlFor="textbox" className="textbox-label">
        Enter Text
      </label>
      <textarea
        id="textbox"
        value={text}
        onChange={handleChange}
        placeholder="Type something..."
        className="textbox-input"
        rows="1"
      />
      {/* <p className="textbox-output">You typed: {text}</p> */}
    </div>
  );
}
