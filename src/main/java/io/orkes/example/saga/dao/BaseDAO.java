package io.orkes.example.saga.dao;

import java.sql.*;

public class BaseDAO {

    private String url;

    public BaseDAO(String url) {
        this.url = url;
    }

    protected Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected Boolean execute(String sql) {
        try (Connection conn = DriverManager.getConnection(this.url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void createTables(String service) {
        switch (service) {
            case "booking":
                createBookingsTableCreationSqlStmt();
                createRidersTableCreationSqlStmt();
                break;
            case "cab_assignment":
                createDriversTableCreationSqlStmt();
                createAssignmentsTableCreationSqlStmt();
                break;
            case "payment":
                createPaymentsTableCreationSqlStmt();
                createPaymentMethodsTableCreationSqlStmt();
                break;
            default:
                System.out.println("Service name not recognized");
        }
    }

    private void createBookingsTableCreationSqlStmt() {
        String sql = "CREATE TABLE bookings (\n"
                + "	bookingId text PRIMARY KEY,\n"
                + "	riderId integer NOT NULL,\n"
                + "	driverId integer,\n"
                + "	pickUpLocation text NOT NULL,\n"
                + "	dropOffLocation text NOT NULL,\n"
                + "	createdAt text NOT NULL,\n"
                + "	status text NOT NULL\n"
                + ");";

        execute(sql);
    }

    private void createRidersTableCreationSqlStmt() {
        String sql = "CREATE TABLE riders (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	contact text\n"
                + ");";

        if(execute(sql)) {
            seedRiders();
        }
    }

    private void createDriversTableCreationSqlStmt() {
        String sql = "CREATE TABLE drivers (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	contact text\n"
                + ");";

        if(execute(sql)) {
            seedDrivers();
        }
    }

    private void createAssignmentsTableCreationSqlStmt() {
        String sql = "CREATE TABLE assignments (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	booking_id text NOT NULL,\n"
                + "	driver_id integer NOT NULL,\n"
                + "	created_at text NOT NULL,\n"
                + "	active boolean NOT NULL\n"
                + ");";

        execute(sql);
    }

    private void createPaymentMethodsTableCreationSqlStmt() {
        String sql = "CREATE TABLE payment_methods (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	rider_id integer NOT NULL,\n"
                + "	details text NOT NULL\n"
                + ");";

        if(execute(sql)) {
            seedPaymentMethods();
        }
    }

    private void createPaymentsTableCreationSqlStmt() {
        String sql = "CREATE TABLE payments (\n"
                + "	payment_id text PRIMARY KEY,\n"
                + "	booking_id text NOT NULL,\n"
                + "	amount number NOT NULL,\n"
                + "	payment_method_id integer,\n"
                + "	status text,\n"
                + "	createdAt text\n"
                + ");";

        execute(sql);
    }



    private void seedRiders() {
        String[] queries = {
                "INSERT INTO riders(name,contact) VALUES('John Smith','+12126781345');",
                "INSERT INTO riders(name,contact) VALUES('Mike Ross','+15466711147');",
                "INSERT INTO riders(name,contact) VALUES('Martha Williams','+12790581941');"
        };

        for (int i = 0; i < queries.length; i++) {
            execute(queries[i]);
        }
    }

    private void seedDrivers() {
        String[] queries = {
                "INSERT INTO drivers(name,contact) VALUES('Wayne Stevens','+12520711467');",
                "INSERT INTO drivers(name,contact) VALUES('Jim Willis','+16466281981');",
                "INSERT INTO drivers(name,contact) VALUES('Tom Cruise','+18659581430');"
        };

        for (int i = 0; i < queries.length; i++) {
            execute(queries[i]);
        }
    }

    private void seedPaymentMethods() {
        String[] queries = {
                "INSERT INTO payment_methods(rider_id, details) VALUES(1,'Credit Card|1123 4425 1345 3323|10/27|123');",
        };

        for (int i = 0; i < queries.length; i++) {
            execute(queries[i]);
        }
    }
}
