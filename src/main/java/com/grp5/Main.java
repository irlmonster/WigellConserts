package com.grp5;

import com.grp5.javaFX.FxManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new FxManager(primaryStage);
    }

    public static void main(String[] args) {

        String url =  "jdbc:mysql://localhost:3306/wigellconcertsdb";
        String username = "root"; // användarnamn till databasen
        String password = "Volvo0414!";   // lösenord till databasen


        launch(args); // Startar JavaFX-applikationen
    }
}
