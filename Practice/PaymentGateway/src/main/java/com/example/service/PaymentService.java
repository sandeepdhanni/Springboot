package com.example.service;



import com.example.entity.Payment;
import com.example.repo.PaymentRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.Refund;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    @Autowired
    private PaymentRepo paymentRepo;

    private RazorpayClient client;

    // Fetches payment details from Razorpay and saves to the database
    public Payment savePaymentDetails(String razorPayPaymentId) throws Exception {
        if (client == null) {
            this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        }

        com.razorpay.Payment razorPayPayment = client.payments.fetch(razorPayPaymentId);
        JSONObject paymentData = new JSONObject(razorPayPayment.toString()); // Convert to JSONObject for optString usage

        Payment payment = new Payment();
        payment.setRazorPayPaymentId(paymentData.getString("id"));

        Long amountInSmallestUnit = paymentData.getLong("amount");
        Double amount = amountInSmallestUnit / 100.0; // Convert from smallest currency unit
        payment.setAmount(amount);

        // Use `optString` method on the JSONObject `paymentData`
        payment.setPaymentMethod(paymentData.optString("method", "N/A"));
        payment.setBank(paymentData.optString("bank", "N/A"));
        payment.setWallet(paymentData.optString("wallet", "N/A"));
        payment.setVpa(paymentData.optString("vpa", "N/A"));
        payment.setCurrency(paymentData.optString("currency", "N/A"));
        payment.setStatus(paymentData.optString("status", "N/A"));
        long paymentTimestamp = paymentData.getLong("created_at");//THE TIMESTAMP WILL COME IN MILLISECONDS
        System.out.println(paymentTimestamp);
        payment.setPaymentDate(new Date(paymentTimestamp * 1000));//WE NEED TO CONVERT TO SECONDS

        payment.setRazorPayOrderId(paymentData.optString("order_id", "N/A"));


        paymentRepo.save(payment);

        return payment;
    }




    public String verifyPaymentStatus(String paymentId) throws Exception {
        if (client == null) {
            this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        }

        com.razorpay.Payment razorPayPayment = client.payments.fetch(paymentId);
        return razorPayPayment.get("status");
    }


    public Map<String, String> getPaymentMethods(String paymentId) throws Exception {
        if (client == null) {
            this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        }

        // Fetch payment details from Razorpay
        com.razorpay.Payment razorPayPayment = client.payments.fetch(paymentId);
        JSONObject paymentData = new JSONObject(razorPayPayment.toString());

        // Extracting payment methods (method, bank, wallet, vpa)
        Map<String, String> paymentMethods = new HashMap<>();
        paymentMethods.put("method", paymentData.optString("method", "N/A"));
        paymentMethods.put("bank", paymentData.optString("bank", "N/A"));
        paymentMethods.put("wallet", paymentData.optString("wallet", "N/A"));
        paymentMethods.put("vpa", paymentData.optString("vpa", "N/A"));

        return paymentMethods;
    }




    public Payment updatePaymentDetails(Integer paymentId, Payment updatedPayment) throws Exception {
        // Find the payment by ID in the database
        Payment existingPayment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new Exception("Payment not found with ID: " + paymentId));

        // Update fields if they are provided
        if (updatedPayment.getAmount() != null) {
            existingPayment.setAmount(updatedPayment.getAmount());
        }
        if (updatedPayment.getStatus() != null) {
            existingPayment.setStatus(updatedPayment.getStatus());
        }
        if (updatedPayment.getPaymentMethod() != null) {
            existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
        }
        if (updatedPayment.getBank() != null) {
            existingPayment.setBank(updatedPayment.getBank());
        }
        if (updatedPayment.getWallet() != null) {
            existingPayment.setWallet(updatedPayment.getWallet());
        }
        if (updatedPayment.getVpa() != null) {
            existingPayment.setVpa(updatedPayment.getVpa());
        }
        if (updatedPayment.getCurrency() != null) {
            existingPayment.setCurrency(updatedPayment.getCurrency());
        }
        if (updatedPayment.getPaymentDate() != null) {
            existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
        }
        if (updatedPayment.getRazorPayOrderId() != null) {
            existingPayment.setRazorPayOrderId(updatedPayment.getRazorPayOrderId());
        }

        // Save updated payment details
        return paymentRepo.save(existingPayment);
    }


    public Payment getPaymentById(Integer paymentId) throws Exception {
        // Find the payment by ID in the database
        return paymentRepo.findById(paymentId)
                .orElseThrow(() -> new Exception("Payment not found with ID: " + paymentId));
    }



    public Payment refundPayment(String paymentId) throws Exception {
        if (client == null) {
            this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        }

        // Prepare the refund request parameters
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("payment_id", paymentId);
        refundRequest.put("amount", 5000); // Optional: Set refund amount in smallest currency unit if partial refund is required

        // Initiate the refund using Razorpay client
        Refund refund = client.payments.refund(refundRequest);
        JSONObject refundData = new JSONObject(refund.toString());

        // Update payment status in the database if needed
        Payment payment = paymentRepo.findByRazorPayPaymentId(paymentId)
                .orElseThrow(() -> new Exception("Payment not found with ID: " + paymentId));
        payment.setStatus("refunded");
        paymentRepo.save(payment);

        return payment;
    }



    public JSONObject getPaymentDetails(String paymentId) throws Exception {
        if (client == null) {
            this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        }

        // Fetch the payment details from Razorpay using paymentId
        com.razorpay.Payment razorPayPayment = client.payments.fetch(paymentId);
        return new JSONObject(razorPayPayment.toString()); // Return the payment details as JSON
    }


}