package com.grp5.javaFX;

import DAOklasser.WcDAO;
import DAOklasser.ConcertDAO;
import DAOklasser.CustomerDAO;
import com.grp5.Booking;
import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;
import com.grp5.entitys.TestFunctions;
import com.grp5.entitys.WC;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

//Skriver detta för att kunna commita klassen
public class CustomerScreen {
    // Skapa TabPane (flikbehållare)
    private TabPane tabPane = new TabPane();

    // Skapar en ny customer
    private Customer loggedInCustomer = new Customer();


    // En lista för att hålla bokningar
    private List<Booking> bookings = new ArrayList<>();

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
        labelTotal.setStyle("-fx-text-fill: white; -fx-font-size: 18");
        labelTotal.setMinWidth(60);

        TextField textTotalSum = new TextField();
        textTotalSum.setPromptText("Totalsumma");
        textTotalSum.setStyle("-fx-font-size: 14px;");
        textTotalSum.setEditable(false);
        textTotalSum.setMaxWidth(120);
        textTotalSum.setMinHeight(30);

        Label label = new Label("kronor");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 18");
        label.setMinWidth(60);

        Label customerLabel = new Label("Inloggad användare: ");
        customerLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        label.setMinWidth(60);

        Label inloggedCustomerLabel = new Label("");
        inloggedCustomerLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        inloggedCustomerLabel.setText(loggedInCustomer.getFirstName());

        // Visar lista med bokade konserter
        Label bookedConsertsHeaderLabel = new Label("");
        bookedConsertsHeaderLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18");
        Label bookedConsertsLabel = new Label("");
        bookedConsertsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16");


        Button btnBookConcert = new Button("Boka konsert");
        btnBookConcert.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        btnBookConcert.setMinWidth(100);
        btnBookConcert.setMinHeight(30);

        Button btnShowBookings = new Button("Visa bokningar");
        btnShowBookings.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        btnShowBookings.setMinWidth(100);
        btnShowBookings.setMinHeight(30);

        // Utloggningsknapp
        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        logoutButton.setOnAction(event -> {
            FxManager fxManager = new FxManager((Stage) logoutButton.getScene().getWindow());
            fxManager.showLoginScreen();
        });


        //för att lägga till konserter i comboboxen
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
        /// ////////////////////BOKA KONCERT KNAPP////////////////////////////////////////////////////////////////////////
        btnBookConcert.setOnAction(e -> {
            String numberOfTickets = textFieldTickets.getText();
            bookConcert(comboBoxConcert, numberOfTickets);
        });

        btnShowBookings.setOnAction(e -> {
            showBookingsInLabel(bookedConsertsLabel);
        });

        HBox logOutbox = new HBox(10);
        logOutbox.setPadding(new Insets(0, 20, 0, 450)); // (top, right, bottom, left)
        logOutbox.getChildren().addAll(logoutButton);


        HBox customerHbox = new HBox();
        customerHbox.getChildren().addAll(customerLabel, inloggedCustomerLabel, logOutbox);
        customerHbox.setAlignment(Pos.TOP_CENTER);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(20);
        hBox.setMaxWidth(600);
        hBox.getChildren().addAll(comboBoxConcert, textFieldTickets, labelTotal, textTotalSum, label);

        HBox hBox2 = new HBox(20);
        hBox2.setAlignment(Pos.CENTER_RIGHT);
        hBox2.setMaxWidth(560);
        hBox2.getChildren().addAll(bookedConsertsHeaderLabel, bookedConsertsLabel, btnShowBookings, btnBookConcert);

        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.setStyle("-fx-background-color: #4682B4");
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(customerHbox, hBox, hBox2);

        tabBooking.setContent(vBox);

        Tab tabSettings = new Tab("Inställningar");
        tabSettings.setClosable(false); // Gör så att användaren inte kan stänga fliken
        tabSettings.setStyle("-fx-font-size: 16px;");

        tabPane.getTabs().addAll(tabBooking, tabSettings);
        Scene scene = new Scene(tabPane, 800, 600);
    }

    private void showBookingsInLabel(Label bookedConsertsLabel) {
        List<Booking> customerBookings = Booking.getBookingsForCustomer(loggedInCustomer);

        if (customerBookings.isEmpty()) {
            bookedConsertsLabel.setText("Du har inga bokningar.");
        } else {
            StringBuilder sb = new StringBuilder("Bokade konserter:\n");

            for (Booking booking : customerBookings) {
                sb.append(booking.getConcert().getArtist_name())
                        .append(" - ")
                        .append(booking.getNumberOfTickets())
                        .append(" biljetter\n");
            }

            bookedConsertsLabel.setText(sb.toString());
        }
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

        // Hämta den valda konserten
        Concerts selectedConcert = comboBoxConcert.getSelectionModel().getSelectedItem();
        if (selectedConcert == null) {
            System.out.println("Vänligen välj en konsert.");
            return;
        }

        // Läs in antal biljetter från textfältet
        int tickets;
        try {
            tickets = Integer.parseInt(numberOfTickets);
            if (tickets <= 0) {
                System.out.println("Vänligen ange ett giltigt antal biljetter.");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Ange ett giltigt antal biljetter.");
            return;
        }
        // Skapa en bokning och sätt informationen

        Concerts concert = new Concerts();

        int customerID = loggedInCustomer.getId();
        int concertID = selectedConcert.getId();

        if (selectedConcert != null && loggedInCustomer != null) {
            WC wc = new WC();

            wc.setConcerts(concert);

            System.out.println("\n" + concert.getId() + "\n");
            wc.setCustomer(loggedInCustomer);
            System.out.println("\n" + customerID + "\n");
            wc.setName(loggedInCustomer.getFirstName());

            WcDAO wcDAO = new WcDAO();
            wcDAO.createTicketWC(wc);

            System.out.println("Biljett bokad för " + loggedInCustomer.getFirstName() + " till " + selectedConcert.getArtist_name());
            TestFunctions.printTickets();
        } else {
            System.out.println("Välj en konsert och se till att du är inloggad!");
        }


       /* // spara bokningen i en lista i minnet istället för i databasen
        booking.addBooking(booking); // stoppar in bokningsobjektet vi skapar ovanför
        System.out.println("Bokning skapad och sparad i minnet: " + booking);
        WcScreen.showAlert("Bokad!", "✅ GREAT SUCCESS!!! ✅" +
                "\nBorat har bekräftat din bokning!", Alert.AlertType.INFORMATION);*/
    }



}
