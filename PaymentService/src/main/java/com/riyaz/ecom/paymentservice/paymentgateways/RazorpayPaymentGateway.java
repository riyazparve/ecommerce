package com.riyaz.ecom.paymentservice.paymentgateways;

import com.razorpay.PaymentLink;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RazorpayPaymentGateway implements IPaymentGateway {

    Logger logger = LoggerFactory.getLogger(RazorpayPaymentGateway.class);

    private final String razorpayKey = "rzp_test_U3a8iKNHOOprkR";
    private final String razorpaySecret = "Y6wCMdHgk8KC7u3XTOylo4Vx";

    @Override
    public String getPaymentLink(String orderId, Long amount, String customerPhone, String customerName, String customerEmail) {
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKey, razorpaySecret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
//            paymentLinkRequest.put("first_min_partial_amount", 100);
            // set expirationDate to 5 mins from now
            Date expirationDate = new Date();
            expirationDate.setTime(expirationDate.getTime() + (5 * 60 * 1000));
            paymentLinkRequest.put("expire_by", expirationDate.getTime());
            logger.info("expirationDate: " + expirationDate.getTime());

            paymentLinkRequest.put("reference_id", orderId);

            paymentLinkRequest.put("description", "Payment for iPhone 18 Pro (1 TB) - Burgundy");

            JSONObject customer = new JSONObject();
            customer.put("name", customerPhone);
            customer.put("contact", customerName);
            customer.put("email", customerEmail);
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);

            JSONObject notes = new JSONObject();
            notes.put("product_name", "iPhone 18 Pro (1 TB) - Burgundy");
            paymentLinkRequest.put("notes", notes);

            paymentLinkRequest.put("callback_url", "https://riyazparve.github.io/profile/");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url");
        } catch (RazorpayException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
