package com.grp5.entitys;
import DAOklasser.AddressDAO;
import DAOklasser.CustomerDAO;
import DAOklasser.WcDAO;
import java.sql.*;

public class CustomerManager {


    public void registerUser(String firstName, String lastName, String birthDate, String phoneNumber,
                             String street, String houseNumber, String postalCode, String city) {
        try {


            AddressDAO addressDAO = new AddressDAO();

            //kontrollerar om adressen redan är registrerad
            Addresses address = addressDAO.findAddress(street, houseNumber, postalCode, city);

            //om adressen inte finns lägger vi till den i databasen
            if (address == null) {
                address = new Addresses();
                address.setStreet(street);
                address.setHouse_number(houseNumber);
                address.setPostal_code(postalCode);
                address.setCity(city);

                addressDAO.saveAddress(address);
            }

            //skapar och sparar kund
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setDateOfBirth(birthDate);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);

            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.saveCustomer(customer);


            TestFunctions.printAllCustomers();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fel vid registrering av användare.");
        }
    }
}