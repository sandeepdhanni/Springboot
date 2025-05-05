// src/pages/MobileVerifyPage.jsx
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

export default function MobileVerifyPage() {
  const { mfaId } = useParams();
  const [options, setOptions] = useState([]);
  const [result, setResult] = useState('');

  useEffect(() => {
    const fetchOptions = async () => {
      const res = await axios.get(`http://localhost:8081/mfa/options/${mfaId}`);
      setOptions(res.data);
    };
    fetchOptions();
  }, [mfaId]);

  const handleSelect = async (number) => {
    const res = await axios.post(`http://localhost:8081/mfa/verify?mfaId=${mfaId}&selectedNumber=${number}`);
    setResult(res.data);
  };

  return (
    <div className="p-6 text-center">
      <h2 className="text-lg font-semibold mb-4">Tap the number you saw on your login screen</h2>
      <div className="flex flex-wrap justify-center gap-4">
        {options.map((num, idx) => (
          <button key={idx} onClick={() => handleSelect(num)} className="bg-blue-500 text-white p-3 rounded text-xl w-20">
            {num}
          </button>
        ))}
      </div>
      {result && <p className="mt-4 font-bold">{result}</p>}
    </div>
  );
}
