package io.orkes.example.saga.service;

import io.orkes.example.saga.dao.BookingDAO;
import io.orkes.example.saga.pojos.Booking;
import io.orkes.example.saga.pojos.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class BookingService {

    private static final BookingDAO bookingDAO = new BookingDAO("jdbc:sqlite:cab_saga.db");

    public static String createBooking(BookingRequest bookingRequest) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Booking booking = new Booking();
        booking.setBookingId(uuidAsString);
        booking.setRiderId(bookingRequest.getRiderId());
        booking.setPickUpLocation(bookingRequest.getPickUpLocation());
        booking.setDropOffLocation(bookingRequest.getDropOffLocation());
        booking.setStatus(Booking.Status.PENDING);

        String error = bookingDAO.insertBooking(booking);

        if (error.isEmpty()) {
            log.info("Created booking with id: {}", booking.getBookingId());
        }
        else {
            log.error("Booking creation failure: {}", error);
            return null;
        }

        return uuidAsString;
    }

    public static Booking getBooking(String bookingId) {
        Booking booking = new Booking();
        bookingDAO.readBooking(bookingId, booking);
        return booking;
    }

    public static boolean assignDriverToBooking(Booking booking, int driverId) {
        booking.setDriverId(driverId);
        booking.setStatus(Booking.Status.ASSIGNED);
        return bookingDAO.updateBooking(booking);
    }

    public static boolean confirmBooking(Booking booking) {
        booking.setStatus(Booking.Status.CONFIRMED);
        log.info("Confirming booking {}", booking.getBookingId());
        return bookingDAO.updateBooking(booking);
    }

    public static boolean cancelBooking(Booking booking) {
        booking.setStatus(Booking.Status.CANCELLED);
        booking.setDriverId(0);
        log.info("Cancelling booking {}", booking.getBookingId());
        return bookingDAO.updateBooking(booking);
    }
}
