package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class DriverNotificationRequest {
    String driverId;
    String dropoff;
    String pickup;
    String bookingId;
}
