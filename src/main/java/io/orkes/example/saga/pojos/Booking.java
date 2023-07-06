package io.orkes.example.saga.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class Booking {
    private String bookingId;
    private String riderId;
    private String driverId;
    private String pickUpLocation;
    private String dropOfLocation;
}
