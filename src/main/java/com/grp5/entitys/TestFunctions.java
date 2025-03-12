package com.grp5.entitys;


import java.sql.*;

public class TestFunctions {
    private static final String URL = "jdbc:mysql://localhost:3306/WigellConcertsDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Root";

    public static void printAllCustomers() {
        String sql = "SELECT * FROM customers";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Lista över alla kunder:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String birthDate = rs.getString("date_of_birth");
                String phone = rs.getString("phone_number");
                int addressId = rs.getInt("address_id");

                System.out.println("ID: " + id + ", Namn: " + firstName + " " + lastName +
                        ", Född: " + birthDate + ", Telefon: " + phone +
                        ", AdressID: " + addressId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void printTickets() {
        String sql = "SELECT * FROM wigells_concert";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Lista över alla kunder:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String Name = rs.getString("name");
                int concertId = rs.getInt("concerts");
                int customerId = rs.getInt("customers");

                System.out.println("ID: " + id + ", Namn: " + Name + " " +
                        ", concertId: " + concertId + ", customerId: " + customerId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}