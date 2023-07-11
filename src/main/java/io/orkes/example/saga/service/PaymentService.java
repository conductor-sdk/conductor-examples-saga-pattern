package io.orkes.example.saga.service;

import io.orkes.example.saga.dao.PaymentMethodDAO;
import io.orkes.example.saga.pojos.Payment;
import io.orkes.example.saga.pojos.PaymentMethod;
import io.orkes.example.saga.pojos.PaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentService {

    private static final PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO("jdbc:sqlite:cab_saga.db");

    public static Payment payForBooking(PaymentRequest paymentRequest) {
        int riderId = paymentRequest.getRiderId();

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethodDAO.readPaymentMethod(riderId, paymentMethod);

        Payment payment = new Payment();

        // Check for payment methods for the rider
        // If exists, create a payment
        if (paymentMethod.getId() > 0) {
            payment.setBookingId(paymentRequest.getBookingId());
            payment.setAmount(40.0);
            payment.setStatus(Payment.Status.SUCCESSFUL);
        } else {
            log.error("Payment method for rider {} not available, failed to pay for booking {}", riderId, paymentRequest.getBookingId());
            payment.setErrorMsg("Rider doesn't have a payment method added");
            payment.setStatus(Payment.Status.FAILED);
        }

        return payment;
    }

}
