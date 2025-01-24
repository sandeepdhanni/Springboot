package com.example.OtpTwilio.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    // Initialize Twilio with Account SID and Auth Token
    static {
        Twilio.init("Account_SID ", "Auth_Token");
    }

    public String sendOtp(String phoneNumber) {
        // Generate a random OTP
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000);

        // Send OTP via SMS using Twilio
        Message message = Message.creator(
                        new PhoneNumber(phoneNumber), // To number
                        new PhoneNumber("Twilio_number"), // From Twilio number
                        "Your OTP is: " + otp) // Message body
                .create();

        // Return the OTP (this can be stored in the database in a real app)
        return otp;
    }
}