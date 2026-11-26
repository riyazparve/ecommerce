package com.riyaz.ecom.paymentservice.paymentgateways;

import com.razorpay.PaymentLink;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Date;

@Slf4j
@Component
//@Primary
public class RazorpayPaymentGateway implements IPaymentGateway {

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    public boolean verifyPaymentLinkCallback(
            String paymentId,
            String paymentLinkId,
            String referenceId,
            String status,
            String signature
    ) {
        if (razorpaySecret == null || razorpaySecret.isBlank()
                || paymentId == null || paymentLinkId == null || referenceId == null
                || status == null || signature == null) {
            return false;
        }

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(razorpaySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            String message = paymentLinkId + "|" + referenceId + "|" + status + "|" + paymentId;
            byte[] expected = HexFormat.of().formatHex(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)))
                    .getBytes(StandardCharsets.US_ASCII);
            return MessageDigest.isEqual(
                    expected,
                    signature.toLowerCase(Locale.ROOT).getBytes(StandardCharsets.US_ASCII)
            );
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Unable to verify Razorpay payment callback", e);
        }
    }

    @Override
    public String getPaymentLink(String orderId, Long amount, String customerPhone, String customerName, String customerEmail) {
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKey, razorpaySecret);
            JSONObject paymentLinkRequest = new JSONObject();
            // UPI Payment link is not supported in test mode
//            paymentLinkRequest.put("upi_link",true); // added to support UPI payments using UPI ID
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
//            paymentLinkRequest.put("first_min_partial_amount", 100);
            // set expirationDate to 5 mins from now
            Date expirationDate = new Date();
            expirationDate.setTime(expirationDate.getTime() + (5 * 60 * 1000));
            paymentLinkRequest.put("expire_by", expirationDate.getTime());
            log.info("expirationDate: " + expirationDate.getTime());

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

            paymentLinkRequest.put("callback_url", "https://unworried-dancing-proponent.ngrok-free.dev/redirectUrl/razorpay");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url");
        } catch (RazorpayException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
