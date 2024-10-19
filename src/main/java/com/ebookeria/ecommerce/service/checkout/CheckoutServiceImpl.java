package com.ebookeria.ecommerce.service.checkout;


import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.entity.TransactionItem;
import com.ebookeria.ecommerce.exception.MyStripeException;
import com.ebookeria.ecommerce.repository.TransactionRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private static final Logger log = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    public CheckoutServiceImpl(TransactionRepository transactionRepository, @Value("${stripe.key.secret}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    @Override
    public String createPayment(Transaction transaction)  {


        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/checkout/success.html")
                .setCancelUrl("http://localhost:8080/api/checkout/cancel.html")
                 .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setClientReferenceId(String.valueOf(transaction.getId()));  // Set the transaction ID as client_reference_id



        for (TransactionItem item : transaction.getTransactionItems()) {


            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("pln")
                    .setUnitAmount((long) (item.getUnitPrice() * 100))
                    .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(item.getEbook().getTitle())
                                    .build())
                    .build();


            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) item.getQuantity())
                            .setPriceData(priceData)
                            .build()
            );
        }

        SessionCreateParams params = paramsBuilder.build();
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new MyStripeException(e);
        }

        log.info("Paymemt status {}",session.getPaymentStatus());

        log.info("Session status {}",session.getPaymentIntent());
        return session.getUrl();

    }
}
