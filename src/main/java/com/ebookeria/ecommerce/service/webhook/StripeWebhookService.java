package com.ebookeria.ecommerce.service.webhook;

import com.stripe.model.Event;

public interface StripeWebhookService {
    void handlePaymentIntentSucceeded(Event event);
    void handlePaymentIntentCanceled(Event event);
}
