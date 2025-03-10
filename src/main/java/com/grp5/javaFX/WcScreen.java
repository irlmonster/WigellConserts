package com.grp5.javaFX;

import DAOklasser.ArenaDAO;
import DAOklasser.ConcertDAO;
import com.grp5.entitys.Addresses;
import com.grp5.entitys.Arena;
import com.grp5.entitys.Concerts;
import com.mysql.cj.Session;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


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
    //////////////////////////////////////      WC TAB     //////////////////////////////////////
    private VBox wcTab() {
        VBox root;
        ComboBox<String> concertDropdown;
        Label customerListLabel;
        Label concertInfoLabel;
        Label customersLabel;

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
        vbox2.setPadding(new Insets(0, 0, 0, 120)); // (top, right, bottom, left)


        // Lägg till innehåll i varje VBox
        vbox1.getChildren().add(new Label());
        vbox2.getChildren().add(new Label());


        // Skapa en HBox för att placera de två VBox-arna bredvid varandra
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna

        HBox hbox2 = new HBox();
        hbox2.setStyle("-fx-padding: 10 0 0 700px;"); //top, right, bottom, left
        hbox.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna


        // Skapa en rubrik och en användartext
        Label headerLabel = new Label("Wigell Conserts");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");



//////////////////////////////////////      KONSERT INFO     //////////////////////////////////////

        // dropdown för konserter
        concertDropdown = new ComboBox<>();
        concertDropdown.setPrefWidth(150);
        concertDropdown.setStyle("-fx-spacing: 0 0 20px 0;");
        concertDropdown.setPromptText("Välj Konsert");
        // label ftt visa info i
        concertInfoLabel = new Label("");
        concertInfoLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black;");


        // Fyller på dropdownen med konserter
        ConcertDAO concertDAO = new ConcertDAO();
        List<Concerts> concerts = concertDAO.getAllConcerts();
        for (Concerts c : concerts) {
            concertDropdown.getItems().add(c.getArtist_name());
        }

        // Logik för att visa info i label
        concertDropdown.setOnAction(event -> {
            String selectedArtist = concertDropdown.getValue();
            if( selectedArtist != null) {
                Concerts selectedConcert = concertDAO.getConcertByArtist(selectedArtist);
                if(selectedConcert != null) {
                    //hämta arena och adress
                    Arena arena = selectedConcert.getArena();
                    Addresses address = (arena != null) ? arena.getAddress() : null;

                    // Sätt infon i en sträng

                    String concertInfo = String.format("🎤 Artist: %s\n🏟️ Arena: %s\n📅 Datum: %s\n💰 Pris: %.2f kr\n🔞 Åldersgräns: %d\n📍 Adress: %s, %s %s",
                            selectedConcert.getArtist_name(),
                            (arena != null) ? arena.getName() : "Okänd arena",
                            selectedConcert.getDate(),
                            selectedConcert.getTicket_price(),
                            selectedConcert.getAge_limit(),
                            (address != null) ? address.getStreet() : "Okänd gata",
                            (address != null) ? address.getCity() : "Okänd stad",
                            (address != null) ? address.getPostal_code() : "Okänd postkod");

                // Uppdatera labeln med konsertInfo
                concertInfoLabel.setText(concertInfo);
                } else {
                    concertInfoLabel.setText("❌ Ingen info hittades för denna konsert.");
                }
            }

        });



//////////////////////////////////////      CUSTOMER INFO     //////////////////////////////////////
        //CustumerListLabel
        customerListLabel = new Label("Besökare");
        customerListLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        customersLabel = new Label();
        customersLabel.setText("använd denna för att lista besökare beroende på vald arena");

        //



        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-font-size: 12px;");
        logoutButton.setOnAction(event -> {
            FxManager fxManager = new FxManager((Stage) logoutButton.getScene().getWindow());
            fxManager.showLoginScreen();
        });



        //  Lägg till stuff i hbox
        hbox.getChildren().addAll(vbox1, vbox2);

        // Lägg till stuff i vbox1
        vbox1.getChildren().addAll(concertDropdown, concertInfoLabel);

        //Lägg till stuff i vbox2
        vbox2.getChildren().addAll(customerListLabel, customersLabel);

        //Lägg till stuff i vboxMid
        vboxMid.getChildren().addAll(hbox2, headerLabel);
        hbox2.getChildren().addAll(logoutButton);

        // Lägg till alla element i den överordnade VBoxen
        root.getChildren().addAll(vboxMid,hbox);
        return root;
    }

    //////////////////////////////////////      ARENA TAB     //////////////////////////////////////
    private VBox wcArenaTab() {
        VBox root = new VBox(20);
        root.setStyle("-fx-font-size: 12px; -fx-padding: 0 0 0 0px;");
        root.setStyle("-fx-background-color: #4682B4;"); // Grön bakgrund för Arena

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox();
        hbox2.setStyle("-fx-padding: 10 0 0 700px;"); //top, right, bottom, left
        hbox2.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna


        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna


        // Textfields
        ComboBox<String> arenaDropDown = new ComboBox<>();
        arenaDropDown.setPromptText("Välj arena / skapa ny...");
        arenaDropDown.setStyle("-fx-max-width: 300px;");

        TextField arenanNameField = new TextField();
        arenanNameField.setPromptText("Ange arenanamn...");
        arenanNameField.setStyle("-fx-max-width: 300px;");

        TextField arenanStreetField = new TextField();
        arenanStreetField.setPromptText("Ange gata...");
        arenanStreetField.setStyle("-fx-max-width: 300px;");

        TextField arenanHouseNumField = new TextField();
        arenanHouseNumField.setPromptText("Ange gatunummer...");
        arenanHouseNumField.setStyle("-fx-max-width: 300px;");

        TextField arenanPostalField = new TextField();
        arenanPostalField.setPromptText("Ange postnummer...");
        arenanPostalField.setStyle("-fx-max-width: 300px;");

        TextField arenanCityField = new TextField();
        arenanCityField.setPromptText("Ange stad...");
        arenanCityField.setStyle("-fx-max-width: 300px;");

        // Lägg till radiobutton
        RadioButton inDoorBtn = new RadioButton("Markera för inomhuskonsert");
        inDoorBtn.setSelected(true);


        Label headerLabel = new Label("Wigell Conserter - Arena");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Fyller listan med arena info och lägger till en ny
        ArenaDAO arenaDAO = new ArenaDAO();
        List<Arena> arenas = arenaDAO.getAllArenas();
        arenaDropDown.getItems().add("- Lägg till ny arena -");
        for (Arena a : arenas) {
            arenaDropDown.getItems().add(a.getName());
        }



        // knappar
        Button addButton = new Button("Lägg till");
        addButton.setOnAction(event -> {
            String arenaName = arenanNameField.getText().trim();
            String arenaStreet = arenanStreetField.getText().trim();
            String arenaHouseNum = arenanHouseNumField.getText().trim();
            String arenaPostal = arenanPostalField.getText().trim();
            String arenaCity = arenanCityField.getText().trim();
            Boolean isIndoor = true;
            inDoorBtn.setSelected(isIndoor);


            // Skapa en ny Addresses-instans
            Addresses newAddress = new Addresses();
            newAddress.setStreet(arenaStreet);
            newAddress.setHouse_number(arenaHouseNum);
            newAddress.setPostal_code(arenaPostal);
            newAddress.setCity(arenaCity);

            if(arenaName.isEmpty() || arenaStreet.isEmpty() || arenaHouseNum.isEmpty() || arenaPostal.isEmpty() || arenaCity.isEmpty()){
                showAlert("Fel", "Alla fält måste fyllas i", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Skapar en ny arena med adress
                Arena newArena = new Arena(arenaName, newAddress, isIndoor);

                // Spara arena till DB
                arenaDAO.saveArena(newArena);

                // Uppdatera dropdown
                arenaDropDown.getItems().add(newArena.getName());

                // Återställer fälten när nya arenan lagts till
                arenanNameField.clear();
                arenanStreetField.clear();
                arenanHouseNumField.clear();
                arenanPostalField.clear();
                arenanCityField.clear();
                inDoorBtn.setSelected(true);
                arenaDropDown.getSelectionModel().clearSelection();

                showAlert("GREAT SUCCESS!", "✅ GREAT SUCCESS! ✅ \nBorat har lagt till arenan i databasen!", Alert.AlertType.INFORMATION);

            } catch (NumberFormatException e) {
                showAlert("Fel", "Postnummer måste vara siffror!", Alert.AlertType.ERROR);
            }
        });


        Button updateButton = new Button("Uppdatera");
//        loginButton.setOnAction(event -> "hej");

        Button removeButton = new Button("Ta bort");
//        loginButton.setOnAction(event -> "hej");

        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-font-size: 12px;");
        logoutButton.setOnAction(event -> {
            FxManager fxManager = new FxManager((Stage) logoutButton.getScene().getWindow());
            fxManager.showLoginScreen();
        });

        // Uppdatera fälten efter vald konsert
        arenaDropDown.setOnAction(event -> {
            String selectedArena = arenaDropDown.getValue();

            if ("- Lägg till ny arena -".equals(selectedArena)) {
                // Tömmer alla fält för att lägga till en ny arena
                arenanNameField.clear();
                arenanStreetField.clear();
                arenanHouseNumField.clear();
                arenanPostalField.clear();
                arenanCityField.clear();
                inDoorBtn.setSelected(true); // Standardval att det är inomhus då
            } else if (selectedArena != null) {
                // Hämtar den valda arenan från databasen via ArenaDAO
                Arena chosenArena = arenaDAO.getArenaByArenaName(selectedArena);

                if (chosenArena != null) {
                    // Sätt arenans namn
                    arenanNameField.setText(chosenArena.getName());

                    // Sätt gatunamn från arenans adress (här antar vi att getAddress() returnerar en Address)
                    if (chosenArena.getAddress() != null) {
                        arenanStreetField.setText(chosenArena.getAddress().getStreet());
                        arenanHouseNumField.setText(chosenArena.getAddress().getHouse_number());
                        arenanPostalField.setText(chosenArena.getAddress().getPostal_code());
                        arenanCityField.setText(chosenArena.getAddress().getCity());
                    } else {
                        // Om ingen adress finns, töm fälten för adressen
                        arenanStreetField.clear();
                        arenanHouseNumField.clear();
                        arenanPostalField.clear();
                        arenanCityField.clear();
                    }

                    // Sätt inomhus-/utomhus-val för arenan
                    inDoorBtn.setSelected(chosenArena.isIndoor());
                }
            }
        });
        // Lägg till stuff i vbox1
        hbox2.getChildren().addAll(logoutButton);
        hbox3.getChildren().addAll(addButton, updateButton, removeButton);
        vbox.getChildren().addAll(headerLabel, arenaDropDown, arenanNameField, arenanStreetField, arenanHouseNumField, arenanPostalField, arenanCityField,  inDoorBtn, hbox3);
        root.getChildren().addAll(hbox2, vbox);
        return root;
    }



    //////////////////////////////////////      CONCERT TAB     //////////////////////////////////////
    private VBox wcConcertTab() {
        VBox root = new VBox(20);
        root.setStyle("-fx-font-size: 12px; -fx-padding: 0 0 0 0px;");
        root.setStyle("-fx-background-color: #4682B4;");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox();
        hbox2.setStyle("-fx-padding: 10 0 0 700px;"); //top, right, bottom, left
        hbox2.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna

        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna


        // Lägg till labels
//        Label radioBtnLabel = new Label("Markera för inomhuskonsert");

        // Textfields
        ComboBox<String> concertDropDown = new ComboBox<>();
        concertDropDown.setPromptText("Välj befintlig konsert...");
        concertDropDown.setStyle("-fx-max-width: 300px;");

        ComboBox<String> arenaDropDown = new ComboBox<>();
        arenaDropDown.setPromptText("Välj arena...");
        arenaDropDown.setStyle("-fx-max-width: 300px;");

        TextField artistNameField = new TextField();
        artistNameField.setPromptText("Ange artist...");
        artistNameField.setStyle("-fx-max-width: 300px;");

        TextField concertDateField = new TextField();
        concertDateField.setPromptText("Ange datum...");
        concertDateField.setStyle("-fx-max-width: 300px;");

        TextField concertPriceField = new TextField();
        concertPriceField.setPromptText("Ange pris... (använd punkt)");
        concertPriceField.setStyle("-fx-max-width: 300px;");

        TextField concertMinAgeField = new TextField();
        concertMinAgeField.setPromptText("Ange åldersgärns...");
        concertMinAgeField.setStyle("-fx-max-width: 300px;");


        // Lägg till radiobutton
        RadioButton inDoorBtn = new RadioButton("Markera för inomhuskonsert");
        inDoorBtn.setSelected(true);


        // LOGIK
        // Fyller konserter i dropdown, lägger till en tom som har tomma fält
        ConcertDAO concertDAO = new ConcertDAO();
        List<Concerts> concerts = concertDAO.getAllConcerts();
        concertDropDown.getItems().add("- Lägg till ny konsert -");
        for (Concerts c : concerts) {
            concertDropDown.getItems().add(c.getArtist_name());
        }

        // Fyller arenor i dropdownen
        ArenaDAO arenaDAO = new ArenaDAO();
        List<Arena> arenas = arenaDAO.getAllArenas();
        for (Arena a : arenas) {
            arenaDropDown.getItems().add(a.getName());
        }


        //LOGGA UT - knapp
        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-font-size: 12px;");
        logoutButton.setOnAction(event -> {
            FxManager fxManager = new FxManager((Stage) logoutButton.getScene().getWindow());
            fxManager.showLoginScreen();
        });

        //  LÄGG TILL NY
        // lägg till-knapp och logik för CREATEknappen
        Button addButton = new Button("Skapa ny");
        addButton.setOnAction(event -> {
            // Hämta alla värden från fälten
            String artistName = artistNameField.getText().trim();
            String selectedArenaName = arenaDropDown.getValue(); // Hämtar vald arena
            String concertDate = concertDateField.getText().trim();
            String concertPriceText = concertPriceField.getText().trim();
            String concertMinAgeText = concertMinAgeField.getText().trim();

            // Kontrollera att alla fält är ifyllda
            if (artistName.isEmpty() || selectedArenaName == null || selectedArenaName.isEmpty() ||
                    concertDate.isEmpty() || concertPriceText.isEmpty() || concertMinAgeText.isEmpty()) {
                showAlert("Fel", "Alla fält måste fyllas i!", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Konvertera numeriska fält
                double concertPrice = Double.parseDouble(concertPriceText);
                int concertMinAge = Integer.parseInt(concertMinAgeText);

                // Hämta vald arena från databasen
                Arena selectedArena = arenaDAO.getArenaByName(selectedArenaName);
                if (selectedArena == null) {
                    showAlert("Fel", "Vald arena hittades inte i databasen!", Alert.AlertType.ERROR);
                    return;
                }

                // Skapa konsert med vald arena
                Concerts newConcert = new Concerts(artistName, concertDate, concertPrice, concertMinAge, selectedArena);

                // Spara konserten i databasen
                concertDAO.saveConcert(newConcert);

                // Uppdatera dropdown-listan
                concertDropDown.getItems().add(newConcert.getArtist_name());

                // Återställ fälten efter att konserten lagts till
                artistNameField.clear();
                concertDateField.clear();
                concertPriceField.clear();
                concertMinAgeField.clear();
                inDoorBtn.setSelected(true);
                arenaDropDown.getSelectionModel().clearSelection();

                showAlert("GREAT SUCCESS!", "✅ GREAT SUCCESS! ✅ \nBorat har lagt till konserten i databasen!", Alert.AlertType.INFORMATION);

            } catch (NumberFormatException e) {
                showAlert("Fel", "Pris och åldersgräns måste vara siffror!", Alert.AlertType.ERROR);
            }
        });

        // UPPDATERA
        // lägg till-knapp och logik för UPDATEknappen
        Button updateButton = new Button("Uppdatera");
        updateButton.setOnAction(event -> {
            System.out.println("🟢 Uppdateringsknappen klickad!");
            String selectedArtist = concertDropDown.getValue();

            if (selectedArtist != null && selectedArtist.equals("- Lägg till ny konsert -")) {
                showAlert("Fel", "Välj en konsert att uppdatera!", Alert.AlertType.ERROR);
                return;
            }

            // Hämta fältens värden
            String artistName = artistNameField.getText().trim();
            String selectedArenaName = arenaDropDown.getValue();
            String concertDate = concertDateField.getText().trim();
            String concertPriceText = concertPriceField.getText().trim();
            String concertMinAgeText = concertMinAgeField.getText().trim();
            boolean IsIndoor = inDoorBtn.isSelected();

            // Kontroll att inget fält är tomt
            if (artistName.isEmpty() || selectedArenaName .isEmpty() || concertDate.isEmpty() ||
                    concertPriceText.isEmpty() || concertMinAgeText.isEmpty()) {
                showAlert("Fel", "Alla fält måste fyllas i!", Alert.AlertType.ERROR);
                return;
            }

            try {
                //Konvertera numeriska fält
                double concertPrice = Double.parseDouble(concertPriceText);
                int concertMinAge = Integer.parseInt(concertMinAgeText);

                // hämta vald konsert från DB
                Concerts selectedConcert = concertDAO.getConcertByArtist(selectedArtist);
                if (selectedConcert == null) {
                    showAlert("Fel", "Vald arena hittades inte!", Alert.AlertType.ERROR);
                    return;
                }


                // Hämta vald arena från DB
                Arena selectedArena = arenaDAO.getArenaByName(selectedArenaName);
                if (selectedArena == null) {
                    System.out.println("❌ Arena hittades inte i databasen!");
                } else {
                    System.out.println("✅ Arena hittad: " + selectedArena.getName());
                }

                // Uppdatera konserten med nya värden
                selectedConcert.setArtist_name(artistName);
                selectedConcert.setDate(concertDate);
                selectedConcert.setTicket_price(concertPrice);
                selectedConcert.setAge_limit(concertMinAge);
                selectedConcert.setArena(selectedArena);

                //spara uppdateringen i databasen
                concertDAO.updateConcerts(selectedConcert);
                //uppdatera dropdownlistan så att det nya namnet visas
                concertDropDown.getItems().set(concertDropDown.getSelectionModel().getSelectedIndex(), artistName);
                concertDropDown.getSelectionModel().select(artistName); // Sätter den till den nya

                showAlert("Uppdaterad!", " ✅ GREAT SUCCESS! ✅ \n Borat har uppdaterat konserten för artisten: " + selectedConcert.getArtist_name() +".", Alert.AlertType.INFORMATION);


            } catch (NumberFormatException e) {
                showAlert("Fel", "Pris och åldersgräns måste vara siffror!", Alert.AlertType.ERROR);
            }

        });




        // lägg till-knapp och logik för DELETEknappen
        Button removeButton = new Button("Ta bort");
        removeButton.setOnAction(event -> {
            String selectedArtist = concertDropDown.getValue();

            // Kolla att en artist är valt
            if (selectedArtist == null || selectedArtist.equals("- Lägg till ny konsert -")) {
                 showAlert("Fel", "Välj en konsert att ta bort!", Alert.AlertType.ERROR);
                 return;
            }

            // Hämta vald konsert från databasen
            Concerts selectedConcert = concertDAO.getConcertByArtist(selectedArtist);
            if (selectedConcert == null) {
                showAlert("Fel", "Konserten hittades inte i databasen!", Alert.AlertType.ERROR);
                return;
            }

            // Bekräfta radering
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Bekräfta radering");
            confirmAlert.setHeaderText("Är du säker på att du vill ta bort konserten?");
            confirmAlert.setHeaderText("Denna åtgärd går inte att ångra.");

            confirmAlert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    //Ta bort konserten ur databasen
                    concertDAO.deleteConcerts(selectedConcert);
                    // ta bort fårn dropdown
                    concertDropDown.getItems().remove(selectedArtist);
                    // Visa nästa konsert i listan
                    if (!concertDropDown.getItems().isEmpty()) {
                        concertDropDown.getSelectionModel().selectFirst();
                    } else {
                        // Om listan är tom, lägg tillbaka "- Lägg till ny konsert -"
                        concertDropDown.getItems().add("- Lägg till ny konsert -");
                        concertDropDown.getSelectionModel().select(0);
                    }

                    // Rensa textfälten
                    artistNameField.clear();
                    concertDateField.clear();
                    concertPriceField.clear();
                    concertMinAgeField.clear();
                    inDoorBtn.setSelected(true);
                    arenaDropDown.getSelectionModel().clearSelection();
                    concertDropDown.getSelectionModel().clearSelection();

                    showAlert("Borttagen!", "✅ Borat kröp in i databasen och slet ut artisten " + selectedConcert.getArtist_name()
                            + " ✅\nPOFF, GONE!!", Alert.AlertType.INFORMATION);
                }
            });

        });







        // Uppdatera fälten efter vald konsert
        concertDropDown.setOnAction(event -> {
            String selectedArtist = concertDropDown.getValue();
            if("- Lägg till ny konsert -".equals(selectedArtist)) {
                // Tömmer alla fält
                artistNameField.clear();
                concertDateField.clear();
                concertMinAgeField.clear();
                concertPriceField.clear();
                inDoorBtn.setSelected(true); // standardval att det är inomhus då
            } else if (selectedArtist != null) {
                Concerts selectedConcert = concertDAO.getConcertByArtist(selectedArtist);
                if (selectedConcert != null) {
                    // Sätt artistens namn
                    artistNameField.setText(selectedConcert.getArtist_name());
                    // Sätt datum
                    concertDateField.setText(selectedConcert.getDate());
                    //Sätt pris
                    concertPriceField.setText(String.valueOf(selectedConcert.getTicket_price()));
                    // sätt åldersgräns
                    concertMinAgeField.setText(String.valueOf(selectedConcert.getAge_limit()));
                    //Hämta arenan
                    Arena arena = selectedConcert.getArena();
                    if (arena != null) {
                        arenaDropDown.setValue(arena.getName()); // sätter rätt arena
                        inDoorBtn.setSelected(arena.isIndoor()); // sätt radiobutton baserat på indoor / outdoor
                    }

                }

            }
        });



        Label headerLabel = new Label("Wigell Conserter - Concerts");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Lägg till stuff i vbox1
        hbox3.getChildren().addAll(addButton, updateButton, removeButton);
        vbox.getChildren().addAll(headerLabel, concertDropDown, arenaDropDown, artistNameField,  concertDateField, concertPriceField,
                concertMinAgeField, inDoorBtn, hbox3);
        hbox2.getChildren().addAll(logoutButton);
        root.getChildren().addAll(hbox2,vbox);
        return root;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    //////////////////////////////////////      ALERT POP-UP     //////////////////////////////////////
    // Metod för att Alert error - vi kan göra något annat om vi vill
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}