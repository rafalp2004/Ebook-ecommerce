package com.ebookeria.ecommerce.service.checkout;

import com.ebookeria.ecommerce.entity.Transaction;
import com.stripe.exception.StripeException;

public interface CheckoutService {
    String createPayment(Transaction transaction) throws StripeException;
}
