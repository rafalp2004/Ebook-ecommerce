package com.ebookeria.ecommerce.exception;

import com.stripe.exception.StripeException;

public class MyStripeException extends RuntimeException {
    public MyStripeException(StripeException e) {
        super(e);
    }
}
