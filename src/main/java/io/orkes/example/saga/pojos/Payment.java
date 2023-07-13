package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class Payment {
    public enum Status {
        PENDING,
        FAILED,
        SUCCESSFUL,
        CANCELED
    }
    private String paymentId;
    private String bookingId;
    private double amount;
    private int paymentMethodId;
    private Status status;
    private long createdAt;
    private String errorMsg;
}
