package io.orkes.example.saga.dao;

import io.orkes.example.saga.pojos.CabAssignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CabAssignmentDAO extends BaseDAO {

    public CabAssignmentDAO(String url) {
        super(url);
    }

    public boolean insertAssignment(CabAssignment cabAssignment) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        String sql = "INSERT INTO assignments(booking_id,driver_id,created_at,active) VALUES(?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cabAssignment.getBookingId());
            pstmt.setInt(2, cabAssignment.getDriverId());
            pstmt.setString(3, nowAsISO);
            pstmt.setBoolean(4, cabAssignment.getActive());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deactivateAssignment(String bookingId) {
        String sql = "UPDATE assignments SET active=? WHERE booking_id=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, false);
            pstmt.setString(2, bookingId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
