package io.orkes.example.saga.service;

import io.orkes.example.saga.pojos.Payment;
import io.orkes.example.saga.pojos.PaymentRequest;

public class PaymentService {
    public static Payment payForBooking(PaymentRequest paymentRequest) {
        Payment pd = new Payment();
        pd.setBookingId(paymentRequest.getBookingId());
        pd.setStatus(Payment.Status.SUCCESSFUL);
        return pd;
    }

}
