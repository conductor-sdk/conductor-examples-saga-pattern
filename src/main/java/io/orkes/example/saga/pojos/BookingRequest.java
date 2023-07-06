package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class BookingRequest {
    private String BookingRequestId;
    private String riderId;
    private String pickUpLocation;
    private String dropOffLocation;
}
