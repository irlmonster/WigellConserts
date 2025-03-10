package com.grp5.entitys;

import java.sql.*;

public class CustomerManager {
    private static final String URL = "jdbc:mysql://localhost:3306/WigellConcertsDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Root";



    public static void regUser(
            String firstname, String lastname, String birthdate, String phone,
            String street,String houseNumber, String city, String postalCode) {

        String addressSQL = "INSERT INTO addresses (street, house_number, postal_code, city) VALUES (?, ?, ?, ?)";
        String userSQL = "INSERT INTO customers (first_name, last_name, date_of_birth, phone_number, address_id) VALUES (?, ?, ?, ?, ?)";

            // Steg 1: skapa anslutning
        try { Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Anslutningen till databasen lyckades!");

                //start upp så att allt kan laddas in i databasen samtidigt
                connection.setAutoCommit(false);


            //lägger till adressen
            int addressId = -1;
            try (PreparedStatement addressStmt = connection.prepareStatement(addressSQL, Statement.RETURN_GENERATED_KEYS)) {
                addressStmt.setString(1, street);
                addressStmt.setString(2, houseNumber);
                addressStmt.setString(3, city);
                addressStmt.setString(4, postalCode);
                addressStmt.executeUpdate();

                try (ResultSet generatedKeys = addressStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        addressId = generatedKeys.getInt(1);
                    }
                }
            }

            if (addressId == -1) {
                throw new SQLException("Misslyckades med att skapa adress, inget ID returnerades.");
            }


            //lägger till användare kopplad till adressen
            try (PreparedStatement userStmt = connection.prepareStatement(userSQL)) {
                userStmt.setString(1, firstname);
                userStmt.setString(2, lastname);
                userStmt.setString(3, birthdate);
                userStmt.setString(4, phone);
                userStmt.setInt(5, addressId);
                userStmt.executeUpdate();
            }

            //lägger till allt till databasen
            connection.commit();
            System.out.println("Användare och adress registrerade!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }