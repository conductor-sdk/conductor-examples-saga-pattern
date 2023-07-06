package io.orkes.example.saga.service;

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

    public static String createBooking(BookingRequest bookingRequest) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Booking booking = new Booking();
        booking.setBookingId(uuidAsString);
        booking.setRiderId(bookingRequest.getRiderId());
        booking.setPickUpLocation(bookingRequest.getPickUpLocation());
        booking.setDropOfLocation(bookingRequest.getDropOffLocation());

        log.info("Created booking with id: {}", booking.getBookingId());

        return uuidAsString;
    }

//    public static String fetchBooking(String bookingId) {
//        Booking booking = new Booking();
//        booking.riderId
//    }
}
