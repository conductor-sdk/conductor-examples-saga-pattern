package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class PaymentDetails {
    public enum Status {
        PENDING,
        FAILED,
        SUCCESSFUL
    }
    private String bookingId;
    private Status status;
}
