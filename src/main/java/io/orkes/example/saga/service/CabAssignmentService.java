package io.orkes.example.saga.service;

import io.orkes.example.saga.dao.CabAssignmentDAO;
import io.orkes.example.saga.pojos.Booking;
import io.orkes.example.saga.pojos.CabAssignment;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
@Slf4j
public class CabAssignmentService {

    private static final CabAssignmentDAO cabAssignmentDAO = new CabAssignmentDAO("jdbc:sqlite:cab_saga.db");

    public static int assignDriver(String bookingId) {
        int driverId = findDriver();

        Booking booking = BookingService.getBooking(bookingId);

        if (booking.getBookingId().isEmpty()) {
            log.error("Booking with id {} not found.", bookingId);
            return 0;
        }

        CabAssignment cabAssignment = new CabAssignment();
        cabAssignment.setBookingId(bookingId);
        cabAssignment.setDriverId(driverId);
        cabAssignment.setActive(true);

        if (!cabAssignmentDAO.insertAssignment(cabAssignment)) {
            log.error("Cab assignment for booking {} failed.", bookingId);
            return 0;
        }

        BookingService.assignDriverToBooking(booking, driverId);

        log.info("Assigned driver {} to booking with id: {}", driverId, bookingId);

        return driverId;
    }

    public static void cancelAssignment(String bookingId) {
        Booking booking = BookingService.getBooking(bookingId);

        if (booking.getBookingId().isEmpty()) {
            log.error("Booking with id {} not found.", bookingId);
        }

        cabAssignmentDAO.deactivateAssignment(bookingId);
    }

    private static int findDriver() {
        Random random = new Random();
        int driverId = 0;
        while (true) {
            driverId = random.nextInt(5);
            if(driverId !=0) break;
        }
        return driverId;
    }
}
