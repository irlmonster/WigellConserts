package com.grp5.javaFX;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WcScreen {

    // Den överordnade containern
    private VBox root;
    private ComboBox<String> concertDropdown;
    private Label customerListLabel;
    private Label concertInfoLabel;

    public WcScreen() {
        // Skapa en överordnad VBox med 20 pixlars mellanrum och centrerat innehåll
        VBox vboxMid= new VBox();
        vboxMid.setAlignment(Pos.CENTER);

        root = new VBox(20);
        root.setStyle("-fx-background-color: #4682B4;");
//        root.setAlignment(Pos.CENTER);

        // Skapa vbox
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();



        // Lägg till innehåll i varje VBox
        vbox1.getChildren().add(new Label());
        vbox2.getChildren().add(new Label());


        // Skapa en HBox för att placera de två VBox-arna bredvid varandra
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna


        Label label2 = new Label("Välj konsert");
        label2.setAlignment(Pos.CENTER);
        concertDropdown = new ComboBox<>();
        concertDropdown.setPrefWidth(150);
        concertDropdown.getItems().addAll("Arch Enemy", "TOKIO HOTEL", "Yorushika", "Stuck in the Sound", "Breaking Benjamin");
        concertDropdown.setPromptText("Konsert....");


        // Skapa en rubrik och en användartext
        Label headerLabel = new Label("Wigell Conserter");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        //CustumerListLabel
        customerListLabel = new Label("apifughbapfeguhbapdfgu");

        //concertInfoLabel
        concertInfoLabel = new Label("aokfngåoäangåoghn");




        //  Lägg till stuff i hbox
        hbox.getChildren().addAll(vbox1, vbox2);

        // Lägg till stuff i vbox1
        vbox1.getChildren().addAll(label2, concertDropdown, customerListLabel);

        //Lägg till stuff i vbox2
        vbox2.getChildren().addAll(customerListLabel);

        //Lägg till stuff i vboxMid
        vboxMid.getChildren().addAll(headerLabel);

        // Lägg till alla element i den överordnade VBoxen
        root.getChildren().addAll(vboxMid,hbox);
    }

    public VBox getRoot() {
        return root;
    }
}

