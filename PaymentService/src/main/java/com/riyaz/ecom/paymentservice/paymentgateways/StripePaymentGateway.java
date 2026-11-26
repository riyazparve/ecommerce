package com.riyaz.ecom.paymentservice.paymentgateways;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class StripePaymentGateway implements IPaymentGateway {

    @Value("${stripe.api.key}")
    private String apiKey;

    private static final String REDIRECT_URL =
            "https://unworried-dancing-proponent.ngrok-free.dev/redirectUrl/stripe?session_id={CHECKOUT_SESSION_ID}";

    public String getCheckoutSessionPaymentStatus(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("Stripe checkout session ID is required");
        }
        try {
            StripeClient client = new StripeClient(apiKey);
            Session session = client.v1().checkout().sessions().retrieve(sessionId);
            return session.getPaymentStatus();
        } catch (StripeException e) {
            throw new IllegalStateException("Unable to retrieve Stripe checkout session", e);
        }
    }

    @Override
    public String getPaymentLink(String orderId, Long amount, String customerPhone, String customerName, String customerEmail) {
        try {
            StripeClient client = new StripeClient(apiKey);
            Price price = getPrice(amount);
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            )
                            // set redirect url in after completion
                            .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl(REDIRECT_URL)
                                            .build())
                                    .build())
                            .build();

            PaymentLink paymentLink = client.v1().paymentLinks().create(params);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Price getPrice(Long amount) {
        try {
            StripeClient client = new StripeClient(apiKey);

            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("INR")
                            .setUnitAmount(amount)
//                            .setRecurring(
//                                    PriceCreateParams.Recurring.builder()
//                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
//                                            .build()
//                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Purchase").build()
                            )
                            .build();

            return client.v1().prices().create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
