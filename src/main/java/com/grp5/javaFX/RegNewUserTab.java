package com.grp5.javaFX;

import com.grp5.entitys.Addresses;
import com.grp5.entitys.CustomerManager;
import com.grp5.entitys.TestFunctions;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import DAOklasser.AddressDAO;
import DAOklasser.CustomerDAO;

import java.util.List;

public class RegNewUserTab {
    private FxManager fxManager;
    private Tab regTab;

    private TextField firstnameField;
    private TextField lastnameField;
    private TextField birthdateField;
    private TextField phoneNumberField;
    private TextField streetField;
    private TextField houseNumberField;
    private TextField postalCodeField;
    private TextField cityField;

    public RegNewUserTab(FxManager fxManager) {
        this.fxManager = fxManager;

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Label headerLabel = new Label("Registrera ny användare");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        firstnameField = new TextField();
        firstnameField.setPromptText("Ange Förnamn...");

        lastnameField = new TextField();
        lastnameField.setPromptText("Ange Efternamn...");

        birthdateField = new TextField();
        birthdateField.setPromptText("Ange Födelsedatum...YYYY-MM-DD");

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Ange Telefonnummber...");

        streetField = new TextField();
        streetField.setPromptText("Ange Gata...");

        houseNumberField = new TextField();
        houseNumberField.setPromptText("Ange Husnummer...");

        postalCodeField = new TextField();
        postalCodeField.setPromptText("Ange Postkod...");

        cityField = new TextField();
        cityField.setPromptText("Ange Stad...");

        Button registerButton = getRegisterButton();

        root.getChildren().addAll(headerLabel, firstnameField, lastnameField, birthdateField, phoneNumberField, streetField, houseNumberField, postalCodeField, cityField, registerButton);


        regTab = new Tab("Registrera användare", root);
    }

    private Button getRegisterButton() {
        Button registerButton = new Button("Registrera");
        registerButton.setOnAction(event -> {
            String firstName = firstnameField.getText();
            String lastName = lastnameField.getText();
            String birthDate = birthdateField.getText();
            String phoneNumber = phoneNumberField.getText();
            String street = streetField.getText();
            String houseNumber = houseNumberField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();



            List<String> fields = List.of(firstName, lastName, birthDate, phoneNumber, street, houseNumber, postalCode, city);
            boolean emptyField = fields.stream().anyMatch(String::isEmpty);

            //ifall att något fält inte är ifyllt
            if(emptyField) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fel!");
                alert.setHeaderText("Alla fält måste fyllas i");
                alert.showAndWait();
                return;
            } else {
                CustomerManager customerManager = new CustomerManager();
                customerManager.registerUser(firstName, lastName, birthDate, phoneNumber, street, houseNumber, postalCode, city);
                fxManager.showLoginScreen();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Meddelande");
                alert.setContentText("Användare skapad");
            }
        });

        return registerButton;
    }

    public Tab getTab() {
        return regTab;
    }
}
