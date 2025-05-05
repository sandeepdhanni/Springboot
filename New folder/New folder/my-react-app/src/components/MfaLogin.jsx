import React, { useState } from 'react';
import axios from 'axios';

export default function MfaLogin() {
  const [userId, setUserId] = useState('');
  const [challenge, setChallenge] = useState(null);
  const [selectedNumber, setSelectedNumber] = useState(null);
  const [result, setResult] = useState('');

  const handleLogin = async () => {
    const response = await axios.post('http://localhost:8081/mfa/initiate?userId=' + userId);
    setChallenge(response.data);
  };

  const fetchOptions = async () => {
    const res = await axios.get('http://localhost:8081/mfa/options/' + challenge.mfaId);
    return res.data;
  };

  const handleVerify = async () => {
    const res = await axios.post(`http://localhost:8081/mfa/verify?mfaId=${challenge.mfaId}&selectedNumber=${selectedNumber}`);
    setResult(res.data);
  };

  return (
    <div className="p-6 max-w-md mx-auto bg-white rounded-xl shadow-md space-y-4">
      <h1 className="text-xl font-bold">Number Matching MFA</h1>

      {!challenge && (
        <div>
          <input
            type="text"
            placeholder="User ID"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            className="border p-2 w-full"
          />
          <button onClick={handleLogin} className="bg-blue-500 text-white p-2 mt-2 w-full rounded">
            Login
          </button>
        </div>
      )}

      {challenge && (
        <div>
          <p className="text-lg font-semibold">Enter the number you see on your login screen: <strong>{challenge.correctNumber}</strong></p>
          <button onClick={async () => setChallenge({ ...challenge, options: await fetchOptions() })} className="bg-gray-300 p-2 rounded mt-2">Show Options</button>

          {challenge.options && (
            <div className="space-x-2 mt-4">
              {challenge.options.map((num, idx) => (
                <button
                  key={idx}
                  className={`p-2 rounded border ${selectedNumber === num ? 'bg-blue-500 text-white' : 'bg-white'}`}
                  onClick={() => setSelectedNumber(num)}>
                  {num}
                </button>
              ))}
              <button onClick={handleVerify} className="bg-green-500 text-white p-2 ml-4 rounded">
                Verify
              </button>
            </div>
          )}

          {result && <p className="mt-4 font-bold">Result: {result}</p>}
        </div>
      )}
    </div>
  );
}
