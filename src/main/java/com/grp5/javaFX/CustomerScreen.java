package com.grp5.javaFX;

import DAOklasser.ConcertDAO;
import DAOklasser.CustomerDAO;
import com.grp5.Booking;
import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

//Skriver detta för att kunna commita klassen
public class CustomerScreen {
    // Skapa TabPane (flikbehållare)
    private TabPane tabPane = new TabPane();

    // Skapar en ny customer
    private Customer loggedInCustomer = new Customer();

    public TabPane getTabPane() {
        return tabPane;
    }

    public CustomerScreen(String username) {
        CustomerDAO customerDAO = new CustomerDAO();
        loggedInCustomer = customerDAO.getCustomerByFirstName(username);

        // Skapa första fliken
        Tab tabBooking = new Tab("Boka konsert");
        tabBooking.setClosable(false); // Gör så att användaren inte kan stänga fliken
        tabBooking.setStyle("-fx-font-size: 16px;");

        ComboBox comboBoxConcert = new ComboBox();
        comboBoxConcert.setPromptText("Konsert");
        comboBoxConcert.setMinWidth(100);
        comboBoxConcert.setMinHeight(30);
        comboBoxConcert.setStyle("-fx-background-color: white; -fx-font-size: 14");

        TextField textFieldTickets = new TextField();
        textFieldTickets.setPromptText("Antal biljetter");
        textFieldTickets.setStyle("-fx-font-size: 14px;");
        textFieldTickets.setMaxWidth(120);
        textFieldTickets.setMinHeight(30);

        Label labelTotal = new Label("Totalt");
        labelTotal.setStyle("-fx-text-fill: white; -fx-font-size: 14");
        labelTotal.setMinWidth(40);

        TextField textTotalSum = new TextField();
        textTotalSum.setPromptText("Totalsumma");
        textTotalSum.setStyle("-fx-font-size: 14px;");
        textTotalSum.setEditable(false);
        textTotalSum.setMaxWidth(120);
        textTotalSum.setMinHeight(30);

        Label label = new Label("kronor");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14");
        label.setMinWidth(60);

        Label customerLabel = new Label("Inloggad användare: ");
        customerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14");
        label.setMinWidth(60);

        Label inloggedCustomerLabel = new Label("");
        inloggedCustomerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14");
        inloggedCustomerLabel.setText(loggedInCustomer.getFirstName());

        Button btnBookConcert = new Button("Boka konsert");
        btnBookConcert.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        btnBookConcert.setMinWidth(100);
        btnBookConcert.setMinHeight(30);

        //Metod för att lägga till konserter i comboboxen
        ConcertDAO concertDAO = new ConcertDAO();
        List<Concerts> concerts = concertDAO.getAllConcerts();
        for (Concerts concert : concerts) {
            comboBoxConcert.getItems().add(concert);
        }

        textFieldTickets.textProperty().addListener((observable, oldValue, newValue) -> {
            // Uppdatera totalpriset när texten i textfältet ändras
            double totalAmount = calculateTotalSum(textFieldTickets, comboBoxConcert);
            textTotalSum.setText(String.format("%.2f", totalAmount));  // Uppdatera med två decimaler
        });

        // Hantera att användaren väljer en konsert och beräkna totalsumman med metoden calculateTotalSum
        comboBoxConcert.setOnAction(e -> {
            String selectedConcert = comboBoxConcert.getSelectionModel().getSelectedItem().toString();
            System.out.println("Vald konsert: " + selectedConcert);
            double totalAmount = calculateTotalSum(textFieldTickets, comboBoxConcert);
            textTotalSum.setText(String.format("%.2f", totalAmount));
        });

        btnBookConcert.setOnAction(e -> {
            String numberOfTickets = textFieldTickets.getText();
            bookConcert(comboBoxConcert, numberOfTickets);
        });

        HBox customerHbox = new HBox();
        customerHbox.getChildren().addAll(customerLabel, inloggedCustomerLabel);
        customerHbox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setMaxWidth(600);
        hBox.getChildren().addAll(comboBoxConcert, textFieldTickets, labelTotal, textTotalSum, label);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER_RIGHT);
        hBox2.setMaxWidth(560);
        hBox2.getChildren().add(btnBookConcert);

        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.setStyle("-fx-background-color: #4682B4");
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(customerHbox, hBox, hBox2);

        tabBooking.setContent(vBox);

        Tab tabSettings = new Tab("Inställningar");
        tabSettings.setClosable(false); // Gör så att användaren inte kan stänga fliken
        tabSettings.setStyle("-fx-font-size: 16px;");

        tabPane.getTabs().addAll(tabBooking, tabSettings);
        Scene scene = new Scene(tabPane, 800, 600);
    }


    public double calculateTotalSum(TextField textFieldTickets, ComboBox<Concerts> comboBoxConcert) {
        try {
            // Omvandlar antalet biljetter till int
            int numberOfTickets = Integer.parseInt(textFieldTickets.getText());

            // Kontrollera om textfältet är tomt
            if(textFieldTickets.getText().isEmpty()) {
                System.out.println("Antal biljetter är tomt");
                System.out.println(textFieldTickets.getText());
            }

            // Kontrollera om en konsert är vald i ComboBox
            Concerts selectedConcert = comboBoxConcert.getSelectionModel().getSelectedItem();
            if (selectedConcert != null) {
                // Beräkna totalbeloppet och returnera det
                double totalAmount = numberOfTickets * selectedConcert.getTicket_price();
                return totalAmount;
            } else {
                // Om ingen konsert är vald, visa ett meddelande
                System.out.println("Vänligen välj en konsert.");
            }
        } catch (NumberFormatException ex) {
            // Om användaren inte anger ett giltigt tal i textfältet
            System.out.println("Ange ett giltigt antal biljetter.");
        }
        return 0; // Om något går fel, returnera 0
    }

    public void bookConcert(ComboBox<Concerts> comboBoxConcert, String numberOfTickets) {
//        // Kontrollera om en kund är inloggad
//        if (loggedInCustomer == null) {
//            System.out.println("Du måste vara inloggad för att boka en konsert.");
//            return;  // Om ingen kund är inloggad, stoppa bokningen
//        }

        // Hämta den valda konserten
        Concerts selectedConcert = comboBoxConcert.getSelectionModel().getSelectedItem();
        if (selectedConcert == null) {
            System.out.println("Vänligen välj en konsert.");
        }

        // Läs in antal biljetter från textfältet
        int tickets = Integer.parseInt(numberOfTickets);
        if (tickets <= 0) {
            System.out.println("Vänligen ange ett giltigt antal biljetter.");
        }

        // Skapa en bokning och sätt informationen
        Booking booking = new Booking();
        booking.setConcert(selectedConcert);  // Sätt vald konsert
        booking.setCustomer(loggedInCustomer);  // Sätt inloggad kund
        booking.setNumberOfTickets(tickets);  // Sätt antal biljetter

        // Spara bokningen i databasen
        saveBooking(booking);
    }

    public void saveBooking(Booking booking) {
        // Skapa SessionFactory och öppna sessionen
        SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Booking.class).buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // Starta en transaktion
            session.beginTransaction();

            // Spara bokningen i databasen
            session.persist(booking);

            // Slutför transaktionen
            session.getTransaction().commit();

            // Stäng sessionen och sessionFactory
            session.close();
            sessionFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
