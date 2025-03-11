package com.grp5.javaFX;

import DAOklasser.CustomerDAO;
import com.grp5.entitys.Customer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginTab {

    private TextField nameField;
    private TextField passwordField;
    private FxManager fxManager;
    private Tab loginTab; // Lägger till en Tab direkt i klassen
    private Customer customer = new Customer();

    public LoginTab(FxManager fxManager) {
        this.fxManager = fxManager;

        // Skapa en VBox för inloggningsinnehållet
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #4682B4;");
        root.setAlignment(Pos.CENTER);

        Label headerLabel = new Label("Wigell Conserter");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Textrutor för användarnamn och lösenord
        nameField = new TextField();
        nameField.setPromptText("Användarnamn...");
        nameField.setStyle("-fx-max-width: 300px;");

        passwordField = new TextField();
        passwordField.setPromptText("Lösenord...");
        passwordField.setStyle("-fx-max-width: 300px;");

        // Inloggningsknapp
        Button loginButton = new Button("Logga in");
        loginButton.setOnAction(event -> login());

        root.getChildren().addAll(headerLabel, nameField, passwordField, loginButton);

        // Skapa en Tab och lägg in VBox som innehåll
        loginTab = new Tab("Inloggning", root);
        loginTab.setClosable(false); // Gör så att fliken inte kan stängas
    }

    private void login() {
        String username = nameField.getText();
        String password = passwordField.getText();

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByFirstName(username); // Hämtar kund från DB

        System.out.println(customer);

        // Om kunden finns i databasen, logga in
        if (customer != null && password.equals("123")) {
            fxManager.showCustomerScreen();
            return;
        }

        // Om inloggning är som admin, logga in som admin
        if (username.equals("Admin") && password.equals("Admin")) {
            fxManager.showWcScreen();
            return;
        }

        // Om inget matchar, skriv ut felmeddelande
        System.out.println("Felaktiga inloggningsuppgifter!");
    }

//    private void login() {
//        String username = nameField.getText();
//        String password = passwordField.getText();
//
//        if (username.equals("Admin") && password.equals("Admin")) {
//            fxManager.showWcScreen(); // Byter till WcScreen
//        } else if (username.equals("Customer") && password.equals("123")) {
//            // Skapa WC-skärmen med dess tre flikar (WC, Arena, Konsert)
//            fxManager.showCustomerScreen();// Byter till CustomerScreen
//        } else {
//            System.out.println("Felaktiga inloggningsuppgifter!");
//        }
//    }

//    private Customer login(Customer customer) {
//        String username = nameField.getText();
//        String password = passwordField.getText();
//        CustomerDAO customerDAO = new CustomerDAO();
//        Customer dbFirstName = customerDAO.getCustomerByFirstName(username);
//        System.out.println(dbFirstName);
//
//        if (username.equals("Admin") && password.equals("Admin")) {
//            fxManager.showWcScreen(); // Byter till WcScreen
//        } else if (username.equals(dbFirstName.getFirst_name()) && password.equals("123")) {
//            fxManager.showCustomerScreen(); // Byter till CustomerScreen
//        } else {
//            System.out.println("Felaktiga inloggningsuppgifter!");
//        }
//        return customer;
//    }

//        if (username.equals("Admin") && password.equals("Admin")) {
//            fxManager.showWcScreen(); // Byter till WcScreen
//        } else if (dbFirstName.getFirst_name().equals(username) && password.equals("123")) {
//            fxManager.showCustomerScreen();// Byter till CustomerScreen
//        } else {
//            System.out.println("Felaktiga inloggningsuppgifter!");
//        }
//        return customer;


    public Tab getTab() {
        return loginTab; // Returnerar hela fliken
    }
}
