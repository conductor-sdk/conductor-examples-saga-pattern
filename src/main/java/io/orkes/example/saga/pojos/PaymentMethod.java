package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class PaymentMethod {
    private int id;
    private int riderId;
    private String details;
}
