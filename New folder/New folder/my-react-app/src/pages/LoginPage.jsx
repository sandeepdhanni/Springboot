// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import axios from 'axios';
import { QRCodeCanvas } from 'qrcode.react'; 

export default function LoginPage() {
  const [userId, setUserId] = useState('');
  const [challenge, setChallenge] = useState(null);
//   const baseUrl = window.location.origin;
const baseUrl = 'http://ipAddress:3000'; 


  const handleLogin = async () => {
    const response = await axios.post(`http://localhost:8081/mfa/initiate?userId=${userId}`);
    setChallenge(response.data);
  };

  return (
    <div className="p-6">
      <h1 className="text-xl font-bold mb-4">Number Matching MFA - Desktop</h1>
      <input
        type="text"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        placeholder="Enter User ID"
        className="border p-2 mb-2 w-full"
      />
      <button onClick={handleLogin} className="bg-blue-600 text-white p-2 rounded w-full">
        Login
      </button>

      {challenge && (
        <div className="mt-6">
          <h2 className="text-lg font-semibold">Your Number: <span className="text-blue-600">{challenge.correctNumber}</span></h2>
          <p className="text-sm mt-2">Scan this QR on your mobile to verify</p>
          <QRCodeCanvas value={`${baseUrl}/verify/${challenge.mfaId}`} size={180} className="mt-4" />
          </div>
      )}
    </div>
  );
}
