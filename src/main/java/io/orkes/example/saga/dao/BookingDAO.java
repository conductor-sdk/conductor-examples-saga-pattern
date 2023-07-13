package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.Booking;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;

public class BookingDAO extends BaseDAO {

    public BookingDAO(String url) {
        super(url);
    }

    public String insertBooking(Booking booking) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        String sql = "INSERT INTO bookings(bookingId,riderId,pickUpLocation,dropOffLocation,createdAt,status) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getBookingId());
            pstmt.setInt(2, booking.getRiderId());
            pstmt.setString(3, booking.getPickUpLocation());
            pstmt.setString(4, booking.getDropOffLocation());
            pstmt.setString(5, nowAsISO);
            pstmt.setString(6, booking.getStatus().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }

    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET driverId=?,pickUpLocation=?,dropOffLocation=?,status=? WHERE bookingId=?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getDriverId());
            pstmt.setString(2, booking.getPickUpLocation());
            pstmt.setString(3, booking.getDropOffLocation());
            pstmt.setString(4, booking.getStatus().name());
            pstmt.setString(5, booking.getBookingId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void readBooking(String bookingId, Booking booking) {
        String sql = "SELECT * FROM bookings WHERE bookingId = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                booking.setBookingId(rs.getString("bookingId"));
                booking.setRiderId(rs.getInt("riderId"));
                booking.setDriverId(rs.getInt("driverId"));
                booking.setPickUpLocation(rs.getString("pickUpLocation"));
                booking.setDropOffLocation(rs.getString("dropOffLocation"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
