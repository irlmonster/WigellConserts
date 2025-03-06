package com.grp5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Välkommen till Wigell Concerts!");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Wigell Concerts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {


        String url =  "jdbc:mysql://localhost:3306/wigellconcertsdb";
        String username = "root"; // användarnamn till databasen
        String password = "root";   // lösenord till databasen

        try {
            // Steg 1: skapa anslutning
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Anslutningen till databasen lyckades!");
            // Steg 2: skapa ett förberett uttalande med en paramter
            String sql = "SELECT * FROM WigellConsertsDB WHERE id = ?"; //  "?" ersätts av värdet från applikationen
            PreparedStatement statement = connection.prepareStatement(sql);

            //Ange värdet för parametern
            int userId = 123;
            statement.setInt(1, userId); // om vi har flera "?" så väljer den ? på index 1

            //Steg 3: Utför frågan och hämta resultatet
            ResultSet resultSet = statement.executeQuery();  // vi hämtar alla (*) värden där userId är 123

            //Steg 4: Bearbeta resultatet (logiken)
            while (resultSet.next()) {
                int  id = resultSet.getInt("userId"); // namnet på ett table
                String name = resultSet.getString("name");
                // hantera resten av resultatet här
                System.out.println("AnvändarID: " + id + ", Namn: " + name);
            }

            //Steg 5: stäng resurser
            resultSet.close();
            statement.close();
            connection.close();

        }   catch (SQLException e) {
            e.printStackTrace();

        }

        launch(args); // Startar JavaFX-applikationen
    }
}
