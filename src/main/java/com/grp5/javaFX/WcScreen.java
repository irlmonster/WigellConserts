package com.grp5.javaFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WcScreen {

    private TabPane tabPane; // TabPane för alla flikar

    public WcScreen() {
        tabPane = new TabPane();

        // Skapa flikarna
        Tab wcTab = new Tab("WC", wcTab());
        Tab arenaTab = new Tab("Arena", wcArenaTab());
        Tab concertTab = new Tab("Konsert", wcConcertTab());

        // Gör så att flikarna inte kan stängas
        wcTab.setClosable(false);
        arenaTab.setClosable(false);
        concertTab.setClosable(false);

        // Lägg till flikarna i TabPane
        tabPane.getTabs().addAll(wcTab, arenaTab, concertTab);
    }

    private VBox wcTab() {
        VBox root;
        ComboBox<String> concertDropdown;
        Label customerListLabel;
        Label concertInfoLabel;

        // Skapa en överordnad VBox med 20 pixlars mellanrum och centrerat innehåll
        VBox vboxMid= new VBox();
        vboxMid.setAlignment(Pos.CENTER);

        root = new VBox(20);
        root.setStyle("-fx-background-color: #4682B4;");
//        root.setAlignment(Pos.CENTER);

        // Skapa vbox
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();

        vbox1.setPadding(new Insets(0, 0, 0, 60)); // (top, right, bottom, left)
        vbox2.setPadding(new Insets(0, 0, 0, 210)); // (top, right, bottom, left)


        // Lägg till innehåll i varje VBox
        vbox1.getChildren().add(new Label());
        vbox2.getChildren().add(new Label());


        // Skapa en HBox för att placera de två VBox-arna bredvid varandra
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna



        // dropdown för konserter
        concertDropdown = new ComboBox<>();
        concertDropdown.setPrefWidth(150);
        concertDropdown.getItems().addAll("Arch Enemy", "TOKIO HOTEL", "Yorushika", "Stuck in the Sound", "Breaking Benjamin");
        concertDropdown.setPromptText("Välj Konsert");


        // Skapa en rubrik och en användartext
        Label headerLabel = new Label("Wigell Conserts");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        //CustumerListLabel
        customerListLabel = new Label("apifughbapfeguhbapdfgu");

        //concertInfoLabel
        concertInfoLabel = new Label("aokfngåoäangåoghn");




        //  Lägg till stuff i hbox
        hbox.getChildren().addAll(vbox1, vbox2);

        // Lägg till stuff i vbox1
        vbox1.getChildren().addAll(concertDropdown, customerListLabel);

        //Lägg till stuff i vbox2
        vbox2.getChildren().addAll(customerListLabel);

        //Lägg till stuff i vboxMid
        vboxMid.getChildren().addAll(headerLabel);

        // Lägg till alla element i den överordnade VBoxen
        root.getChildren().addAll(vboxMid,hbox);
        return root;
    }

    private VBox wcArenaTab() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #4682B4;"); // Grön bakgrund för Arena

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);


        // Textfields
        TextField arenanNameField = new TextField();
        arenanNameField.setPromptText("Ange arenanamn...");
        arenanNameField.setStyle("-fx-max-width: 300px;");

        TextField arenanAddressField = new TextField();
        arenanAddressField.setPromptText("Ange adress...");
        arenanAddressField.setStyle("-fx-max-width: 300px;");


        Label headerLabel = new Label("Wigell Conserter - Arena");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // knappar
        Button addButton = new Button("Lägg till");
//        loginButton.setOnAction(event -> "hej");

        Button updateButton = new Button("Uppdatera");
//        loginButton.setOnAction(event -> "hej");

        Button removeButton = new Button("Ta bort");
//        loginButton.setOnAction(event -> "hej");


        // Lägg till stuff i vbox1
        vbox.getChildren().addAll(arenanNameField, arenanAddressField, addButton, updateButton, removeButton);
        root.getChildren().addAll(headerLabel, vbox);
        return root;
    }

    private VBox wcConcertTab() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #4682B4;"); // Grön bakgrund för Arena

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);


        // Textfields
        TextField artistNameField = new TextField();
        artistNameField.setPromptText("Ange artist...");
        artistNameField.setStyle("-fx-max-width: 300px;");

        TextField concertAddressField = new TextField();
        concertAddressField.setPromptText("Ange adress...");
        concertAddressField.setStyle("-fx-max-width: 300px;");

        TextField concertDateField = new TextField();
        concertDateField.setPromptText("Ange datum...");
        concertDateField.setStyle("-fx-max-width: 300px;");

        TextField concertPriceField = new TextField();
        concertPriceField.setPromptText("Ange pris...");
        concertPriceField.setStyle("-fx-max-width: 300px;");

        TextField concertMinAgeField = new TextField();
        concertMinAgeField.setPromptText("Ange åldersgärns...");  //  sparar plats
        concertMinAgeField.setStyle("-fx-max-width: 300px;");

        TextField concertArenaField = new TextField();
        concertArenaField.setPromptText("KANSKE DROPDOWN FÖR ARENOR?");  //  sparar plats
        concertArenaField.setStyle("-fx-max-width: 300px;");


        // lägg till-knapp
        Button addButton = new Button("Lägg till");
//        loginButton.setOnAction(event -> "hej");

        Button updateButton = new Button("Uppdatera");
//        loginButton.setOnAction(event -> "hej");

        Button removeButton = new Button("Ta bort");
//        loginButton.setOnAction(event -> "hej");


        Label headerLabel = new Label("Wigell Conserter - Concerts");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Lägg till stuff i vbox1
        vbox.getChildren().addAll(artistNameField, concertAddressField, concertDateField, concertPriceField,
                concertMinAgeField, concertArenaField, addButton, updateButton, removeButton);
        root.getChildren().addAll(headerLabel, vbox);
        return root;
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
