package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class PaymentRequest {
    private String bookingId;
    private int riderId;
}
