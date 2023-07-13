package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.Payment;
import io.orkes.example.saga.pojos.PaymentMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PaymentsDAO extends BaseDAO {

    public PaymentsDAO(String url) {
        super(url);
    }

//    public String insertPaymentMethod(Booking booking) {
//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
//        df.setTimeZone(tz);
//        String nowAsISO = df.format(new Date());
//
//        String sql = "INSERT INTO bookings(bookingId,riderId,pickUpLocation,dropOffLocation,createdAt,status) VALUES(?,?,?,?,?,?)";
//
//        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, booking.getBookingId());
//            pstmt.setInt(2, booking.getRiderId());
//            pstmt.setString(3, booking.getPickUpLocation());
//            pstmt.setString(4, booking.getDropOffLocation());
//            pstmt.setString(5, nowAsISO);
//            pstmt.setString(6, booking.getStatus().name());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return e.getMessage();
//        }
//        return "";
//    }

    public void readPaymentMethod(int riderId, PaymentMethod paymentMethod) {
        String sql = "SELECT id, details FROM payment_methods WHERE rider_id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, riderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                paymentMethod.setId(rs.getInt("id"));
                paymentMethod.setDetails(rs.getString("details"));
                paymentMethod.setRiderId(riderId);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String insertPayment(Payment payment) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        String sql = "INSERT INTO payments(payment_id, booking_id, amount, payment_method_id, createdAt, status) VALUES(?,?,?,?,?,?);";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, payment.getPaymentId());
            pstmt.setString(2, payment.getBookingId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setInt(4, payment.getPaymentMethodId());
            pstmt.setString(5, nowAsISO);
            pstmt.setString(6, payment.getStatus().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }

    public String updatePayment(Payment payment) {
        String sql = "UPDATE payments SET amount=?, payment_method_id=?, status=? WHERE payment_id=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, payment.getAmount());
            pstmt.setInt(2, payment.getPaymentMethodId());
            pstmt.setString(3, payment.getStatus().name());
            pstmt.setString(4, payment.getPaymentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "";
    }
}
