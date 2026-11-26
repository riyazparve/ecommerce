package com.riyaz.ecom.paymentservice;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public final class PaymentServiceConfigurationProbe {
    private PaymentServiceConfigurationProbe() {
    }

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(PaymentServiceApplication.class)
                .web(WebApplicationType.NONE)
                .run(args)) {
            System.out.println("CONFIGURED_STRIPE_API_KEY=["
                    + context.getEnvironment().getProperty("stripe.api.key", "<missing>") + "]");
        }
    }
}