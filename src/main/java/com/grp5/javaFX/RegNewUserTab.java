package com.grp5.javaFX;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegNewUserTab {
    private FxManager fxManager;
    private Tab regTab;

    public RegNewUserTab(FxManager fxManager) {
        this.fxManager = fxManager;

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Label headerLabel = new Label("Registrera ny användare");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Ange användarnamn...");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Ange lösenord...");

        Button registerButton = new Button("Registrera");
        registerButton.setOnAction(event -> System.out.println("Registrering ej implementerad ännu"));

        root.getChildren().addAll(headerLabel, usernameField, passwordField, registerButton);

        regTab = new Tab("Registrera användare", root);
    }

    public Tab getTab() {
        return regTab;
    }
}
