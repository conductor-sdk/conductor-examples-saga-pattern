package io.orkes.example.saga.service;

import io.orkes.example.saga.pojos.Booking;
import io.orkes.example.saga.pojos.BookingRequest;
import io.orkes.example.saga.pojos.CabAssignmentRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
@Slf4j
public class CabAssignmentService {
    public static String assignDriver(CabAssignmentRequest cabAssignmentRequest) {
        String driverId = "driver_1";

        log.info("Assigned driver {} to booking with id: {}", driverId, cabAssignmentRequest.getBookingId());

        return "driver_1";
    }
}
