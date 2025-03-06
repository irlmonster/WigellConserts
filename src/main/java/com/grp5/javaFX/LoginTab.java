package com.grp5.javaFX;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginTab {


    private TextField nameField;
    private TextField passwordField;
    private Label statusLabel;
    private VBox root;
    private FxManager fxManager;


    public LoginTab(FxManager fxManager) {
        this.fxManager = fxManager;

        // Skapa en VBox med 20 pixlars mellanrum och centrerat innehåll
        root = new VBox(20);
        root.setStyle("-fx-background-color: #4682B4;");
        root.setAlignment(Pos.CENTER);

        Label headerLabel = new Label("Wigell Conserter");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Textruta för användarnamn
        nameField = new TextField();
        nameField.setPromptText("Användarnamn...");
        nameField.setStyle("-fx-max-width: 300px;");

        // Textruta för lösenord
        passwordField = new TextField();
        passwordField.setPromptText("Lösenord...");
        passwordField.setStyle("-fx-max-width: 300px;");

        // Skapa inloggningsknappen med eventhanterare
        Button loginButton = new Button("Logga in");
        loginButton.setOnAction(event -> fxManager.showWcScreen());

        root.getChildren().addAll(headerLabel, nameField, passwordField, loginButton);
    }


    public void Login() {
        String username = nameField.getText();
        String password = passwordField.getText();

        // Enkel kontroll

       if (username.equals("Admin") && password.equals("Admin")) {
           // byter till WcScreen
           fxManager.showWcScreen();
       } else {
           System.out.println("Felaktiga inloggningsuppgifter!");
       }

    }

    public VBox getContent() {
        return root;
    }


}