package io.orkes.example.saga.service;

import io.orkes.example.saga.pojos.PaymentDetails;
import io.orkes.example.saga.pojos.PaymentRequest;

public class PaymentService {
    public static PaymentDetails payForBooking(PaymentRequest paymentRequest) {
        PaymentDetails pd = new PaymentDetails();
        pd.setBookingId(paymentRequest.getBookingId());
        pd.setStatus(PaymentDetails.Status.SUCCESSFUL);
        return pd;
    }

}
