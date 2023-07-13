package io.orkes.example.saga.pojos;

import lombok.Data;

import java.util.EnumMap;

@Data
public class Booking {

    public enum Status {
        PENDING,
        ASSIGNED,
        CONFIRMED,
        CANCELLED
    }

    private String bookingId;
    private int riderId;
    private int driverId;
    private String pickUpLocation;
    private String dropOffLocation;
    private long createdAt;
    private Status status;
}
