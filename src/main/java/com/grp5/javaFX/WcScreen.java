package com.grp5.javaFX;

import DAOklasser.*;
import com.grp5.entitys.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.*;
import java.util.List;
import java.util.Map;


public class WcScreen {

    private TabPane tabPane; // TabPane för alla flikar
    private ObservableList<String> arenaObservableList = FXCollections.observableArrayList();
    private ComboBox<String> arenaDropDown = new ComboBox<>(arenaObservableList);

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

//        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
//            if (newTab.getText().equals("Konsert")) {
//                updateArenaDropdownForConcerts(arenaDropDown);
//            }
//        });

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
        VBox vbox1 = new VBox(20);
        VBox vbox2 = new VBox(20);
        VBox vbox3 = new VBox(20);

        vbox1.setPadding(new Insets(0, 0, 0, 60)); // (top, right, bottom, left)
        vbox2.setPadding(new Insets(0, 0, 0, 70)); // (top, right, bottom, left)
        vbox3.setPadding(new Insets(35, 20, 0, 80)); // (top, right, bottom, left)

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
        Label headerLabel = new Label("Wigell Conserts 🎤");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");

        //CustumerListLabel
        customerListLabel = new Label("Besökare");
        customerListLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
        customersLabel = new Label();
        customersLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: white;");
        customersLabel.setText("");


//////////////////////////////////////      KONSERT INFO     //////////////////////////////////////
        // dropdown för konserter
        concertDropdown = new ComboBox<>();
        concertDropdown.setMinWidth(150);
        concertDropdown.setMinHeight(30);
        concertDropdown.setStyle("-fx-background-color: white; -fx-font-size: 14;");
        concertDropdown.setPromptText("Välj Konsert");
        // label ftt visa info i
        concertInfoLabel = new Label("");
        concertInfoLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white;");


        ComboBox<String> customerDropdown;
        customerDropdown = new ComboBox<>();
        customerDropdown.setMinWidth(110);
        customerDropdown.setMinHeight(30);
        customerDropdown.setStyle("-fx-background-color: white; -fx-font-size: 14;");
        customerDropdown.setPromptText("Välj kund");



        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer c : customers) {
            customerDropdown.getItems().add(c.getFirstName());
        }


        Button deleteCstmrChosenConcertBtn = new Button("Avboka till koncert");
        deleteCstmrChosenConcertBtn.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        deleteCstmrChosenConcertBtn.setMinWidth(100);
        deleteCstmrChosenConcertBtn.setMinHeight(30);
        deleteCstmrChosenConcertBtn.setOnAction(event -> {
            // Hämta den valda kundens namn från customerDropdown
            String selectedCustomerName = customerDropdown.getValue();
            // Hämta den valda konserten från concertDropdown
            String selectedArtist = concertDropdown.getValue();

            if (selectedCustomerName == null || selectedCustomerName.isEmpty() ||
                    selectedArtist == null || selectedArtist.isEmpty()) {
                showAlert("Fel", "Välj både en kund och en konsert!", Alert.AlertType.ERROR);
                return;
            }

            ConcertDAO concertDAO = new ConcertDAO();
            // Hämta Customer-objektet via CustomerDAO
            Customer selectedCustomer = customerDAO.getCustomerByFirstName(selectedCustomerName);
            // Hämta Concerts-objektet via ConcertDAO
            Concerts selectedConcert = concertDAO.getConcertByArtist(selectedArtist);

            if (selectedCustomer == null || selectedConcert == null) {
                showAlert("Fel", "Kunden eller konserten hittades inte!", Alert.AlertType.ERROR);
                return;
            }

            // Anropa metoden i WcDAO för att ta bort biljetterna
            WcDAO wcDAO = new WcDAO();
            wcDAO.deleteTicketsForCustomerAndConcert(selectedCustomer, selectedConcert);

            showAlert("Borttagen", "Alla biljetter för " + selectedCustomer.getFirstName() +
                    " till konsert " + selectedConcert.getArtist_name() + " har tagits bort!", Alert.AlertType.INFORMATION);

            // Eventuellt: Uppdatera customersLabel med aktuell info, t.ex. genom att anropa samma logik som i din concertDropdown.onAction
        });



        Button deleteCstmrBtn = new Button("Avboka alla");
        deleteCstmrBtn.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        deleteCstmrBtn.setMinWidth(100);
        deleteCstmrBtn.setMinHeight(30);
        deleteCstmrBtn.setOnAction(event -> {
            String selectedCustomerName = customerDropdown.getValue();
            if (selectedCustomerName == null || selectedCustomerName.isEmpty()) {
                showAlert("Fel", "Välj en kund!", Alert.AlertType.ERROR);
                return;
            }
            Customer selectedCustomer = customerDAO.getCustomerByFirstName(selectedCustomerName);
            if (selectedCustomer == null) {
                showAlert("Fel", "Kunden hittades inte!", Alert.AlertType.ERROR);
                return;
            }
            WcDAO wcDAO = new WcDAO();
            wcDAO.deleteTicketsForCustomer(selectedCustomer);
            showAlert("Borttagen", "Borat har tagit bort alla biljetter för "
                    + selectedCustomer.getFirstName()
                    , Alert.AlertType.INFORMATION);
        });


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

                    String concertInfo = String.format("🎤 Artist: %s\n🏟️ Arena: %s\n📅 Datum: %s" +
                                    "\n💰 Pris: %.2f kr\n🔞 Åldersgräns: %d\n📍 Adress: %s, %s %s",
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


                    // Hämta och aggregera biljetter för den valda konserten
                    WcDAO wcDAO = new WcDAO();
                    Map<Customer, Long> ticketsMap = wcDAO.getTicketCountsForConcert(selectedConcert);
                    StringBuilder sb = new StringBuilder();
                    if (ticketsMap.isEmpty()) {
                        sb.append("Inga bokningar för denna konsert.");
                    } else {
                        for (Map.Entry<Customer, Long> entry : ticketsMap.entrySet()) {
                            Customer c = entry.getKey();
                            Long count = entry.getValue();
                            sb.append(c.getFirstName()).append(" ").append(c.getLastName())
                                    .append(" - ").append(count).append(" biljetter\n");
                        }
                    }
                    customersLabel.setText(sb.toString());

                } else {
                    concertInfoLabel.setText("❌ Ingen info hittades för denna konsert.");
                    customersLabel.setText("");
                }
            }

        });



//////////////////////////////////////      CUSTOMER INFO     //////////////////////////////////////

        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        logoutButton.setMinWidth(100);
        logoutButton.setMinHeight(30);
        logoutButton.setOnAction(event -> {
            FxManager fxManager = new FxManager((Stage) logoutButton.getScene().getWindow());
            fxManager.showLoginScreen();
        });

        //  Lägg till stuff i hbox
        hbox.getChildren().addAll(vbox1, vbox2, vbox3);

        // Lägg till stuff i vbox1
        vbox1.getChildren().addAll(concertDropdown, concertInfoLabel);

        //Lägg till stuff i vbox2
        vbox2.getChildren().addAll(customerListLabel, customersLabel);

        //Lägg till stuff i vbox3
        vbox3.getChildren().addAll(customerDropdown, deleteCstmrBtn, deleteCstmrChosenConcertBtn);

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
        root.setStyle("-fx-background-color: #4682B4;");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox();
        hbox2.setStyle("-fx-padding: 10 0 0 700px;"); //top, right, bottom, left
        hbox2.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna

        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(10); // Sätter ett mellanrum mellan VBox-arna

        // Textfields
        ComboBox<String> arenaDropDown = new ComboBox<>(arenaObservableList);
        arenaDropDown.setMinWidth(100);
        arenaDropDown.setMinHeight(30);
        arenaDropDown.setStyle("-fx-background-color: white; -fx-font-size: 14");
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
        inDoorBtn.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
        inDoorBtn.setSelected(true);


        Label headerLabel = new Label("Wigell Conserter - Arena 🎤");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Fyller listan med arena info och lägger till en ny
        ArenaDAO arenaDAO = new ArenaDAO();
        List<Arena> arenas = arenaDAO.getAllArenas();
        arenaDropDown.getItems().add("- Lägg till ny arena -");
        for (Arena a : arenas) {
            arenaDropDown.getItems().add(a.getName());
        }





        // knappar
        Button addButton = new Button("Lägg till");
        addButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        addButton.setMinWidth(100);
        addButton.setMinHeight(30);
        addButton.setOnAction(event -> {
            String arenaName = arenanNameField.getText().trim();
            String arenaStreet = arenanStreetField.getText().trim();
            String arenaHouseNum = arenanHouseNumField.getText().trim();
            String arenaPostal = arenanPostalField.getText().trim();
            String arenaCity = arenanCityField.getText().trim();
            boolean isIndoor = inDoorBtn.isSelected();

            // Kontrollera att alla fält är ifyllda
            if (arenaName.isEmpty() || arenaStreet.isEmpty() || arenaHouseNum.isEmpty() ||
                    arenaPostal.isEmpty() || arenaCity.isEmpty()) {
                showAlert("Fel", "Alla fält måste fyllas i", Alert.AlertType.ERROR);
                return;
            }

            try {

                AddressDAO addressDAO = new AddressDAO();

                //kontrollerar om adressen redan är registrerad
                Addresses address = addressDAO.findAddress(arenaStreet, arenaHouseNum, arenaPostal, arenaCity);

                //om adressen inte finns lägger vi till den i databasen
                if (address == null) {
                    address = new Addresses();
                    address.setStreet(arenaStreet);
                    address.setHouse_number(arenaHouseNum);
                    address.setPostal_code(arenaPostal);
                    address.setCity(arenaCity);

                    addressDAO.saveAddress(address);
                }

                //skapar och sparar kund
                Arena arena = new Arena();
                arena.setName(arenaName);
                arena.setAddress(address);
                arena.setIndoor(isIndoor);


                ArenaDAO newarenaDAO = new ArenaDAO();
                newarenaDAO.saveArena(arena);

                showAlert("GREAT SUCCESS!", "✅ GREAT SUCCESS! ✅ \nArenan har lagts till i databasen!", Alert.AlertType.INFORMATION);
                updateArenaObservableList();

            } catch (NumberFormatException e) {
                showAlert("Fel", "Postnummer måste vara siffror!", Alert.AlertType.ERROR);
            }
        });




        Button updateButton = new Button("Uppdatera");
        updateButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        updateButton.setMinWidth(100);
        updateButton.setMinHeight(30);
        updateButton.setOnAction(event -> {
            System.out.println("🟢 Uppdateringsknappen klickad!");
            arenaDropDown.getValue();
            String selectedArena = arenaDropDown.getValue();

            if(selectedArena == null){
                showAlert("Fel", "Vänligen välj en arena du vill uppdatera", Alert.AlertType.ERROR);
                return;
            }

            String arenaName = arenanNameField.getText().trim();
            String arenaStreet = arenanStreetField.getText().trim();
            String arenaHouseNum = arenanHouseNumField.getText().trim();
            String arenaPostal = arenanPostalField.getText().trim();
            String arenaCity = arenanCityField.getText().trim();
            Boolean isIndoor = true;
            inDoorBtn.setSelected(isIndoor);

            if(arenaName.isEmpty() || arenaStreet.isEmpty() || arenaHouseNum.isEmpty() || arenaPostal.isEmpty() || arenaCity.isEmpty()){
                showAlert("Fel", "Alla fält måste fyllas i", Alert.AlertType.ERROR);
                return;
            }

            try{


                // Hämtar vald arena från databasen
                Arena chosenArena = arenaDAO.getArenaByName(selectedArena);
                if (selectedArena == null) {
                    System.out.println("❌ Arena hittades inte i databasen!");
                } else {
                    System.out.println("✅ Arena hittad: " + chosenArena.getName());
                }

                //Uppdaterar address och arena
                Addresses newAddress = new Addresses();
                newAddress.setStreet(arenaStreet);
                newAddress.setHouse_number(arenaHouseNum);
                newAddress.setPostal_code(arenaPostal);
                newAddress.setCity(arenaCity);

                AddressDAO addressDAO = new AddressDAO();
                addressDAO.saveAddress(newAddress);

                chosenArena.setName(arenanNameField.getText().trim());
                chosenArena.setAddress(newAddress);
                inDoorBtn.setSelected(isIndoor);

                // Sparar uppdatering av arena
                arenaDAO.updateArena(chosenArena);

                //Uppdaterar dropdown så nya namnet är visas i dropdown
                arenaDropDown.getItems().set(arenaDropDown.getSelectionModel().getSelectedIndex(), arenaName);
                arenaDropDown.getSelectionModel().select(arenaName);

                showAlert("Uppdaterad!", " ✅ GREAT SUCCESS! ✅ \n Borat har uppdaterat arenan: " + chosenArena.getName() +".", Alert.AlertType.INFORMATION);

            } catch (NumberFormatException e){
                showAlert("Fel", "Postnummer måste vara siffror!", Alert.AlertType.ERROR);
            }

        });

        Button removeButton = new Button("Ta bort");
        removeButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        removeButton.setMinWidth(100);
        removeButton.setMinHeight(30);
        removeButton.setOnAction(event -> {
            String selectedArena = arenaDropDown.getValue();
            Arena thisArena = arenaDAO.getArenaByArenaName(selectedArena);

            // Kollar att en arena är vald
            if(selectedArena == null || selectedArena.equals("- Lägg till ny arena -")) {
                showAlert("Fel", "Du måste välja en arena att ta bort", Alert.AlertType.ERROR);
                return;
            }

            ConcertDAO concertDAO = new ConcertDAO();
            long count = concertDAO.countConcertsForArena(thisArena.getId());
            if (count > 0) {
                // Visa ett felmeddelande: Arenan har bokade konserter, och kan inte tas bort.
                showAlert("Fel", "Kan inte ta bort arenan, den används i " + count + " konserter!", Alert.AlertType.ERROR);
                return;
            }





            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Bekräfta radering");
            confirmAlert.setHeaderText("Är du säker på att du vill ta bort konserten?");
            confirmAlert.setHeaderText("Denna åtgärd går inte att ångra.");
            confirmAlert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    // Tar bort arenan från databasen
                    arenaDAO.deleteArena(thisArena);
                    // Tar bort arenan från dropdown
                    arenaDropDown.getItems().remove(selectedArena);
                    //Visa nästa arena i listan
                    if(!arenaDropDown.getItems().isEmpty()) {
                        arenaDropDown.getSelectionModel().selectFirst();
                    } else {
                        // Om listan är tom sätt till - Lägg till ny arena -
                        arenaDropDown.getItems().add("- Lägg till ny arena -");
                        arenaDropDown.getSelectionModel().select(0);
                    }

                    // rensa textfälten
                    arenanNameField.clear();
                    arenanStreetField.clear();
                    arenanHouseNumField.clear();
                    arenanPostalField.clear();
                    arenanCityField.clear();
                    inDoorBtn.setSelected(true);
                    arenaDropDown.getSelectionModel().clearSelection();

                    showAlert("Borttagen!", "✅ Borat kröp in i databasen och slet ut artisten " + thisArena.getName()
                            + " ✅\nPOFF, GONE!!", Alert.AlertType.INFORMATION);
                }
            });

        });

        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        logoutButton.setMinWidth(100);
        logoutButton.setMinHeight(30);
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
        vbox.getChildren().addAll(headerLabel, arenaDropDown, arenanNameField, arenanStreetField,
                arenanHouseNumField, arenanPostalField, arenanCityField,  inDoorBtn, hbox3);
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


        // Textfields
        ComboBox<Concerts> concertDropDown = new ComboBox<>();
        concertDropDown.setMinWidth(100);
        concertDropDown.setMinHeight(30);
        concertDropDown.setStyle("-fx-background-color: white; -fx-font-size: 14");
        concertDropDown.setPromptText("Välj befintlig konsert...");
        concertDropDown.setStyle("-fx-max-width: 300px;");

        // Sätt en converter så att vi kan visa t.ex. "Artist (Datum)"
        concertDropDown.setConverter(new StringConverter<Concerts>() {
            @Override
            public String toString(Concerts concert) {
                if (concert == null) {
                    return "";
                }
                // Om vi har en dummy-post, visa den texten direkt
                if (concert.getId() == 0) {
                    return concert.getArtist_name();
                }
                return concert.getArtist_name() + " (" + concert.getDate() + ")";
            }
            @Override
            public Concerts fromString(String string) {
                return null; // Används inte
            }
        });


        ComboBox<String> arenaDropDown = new ComboBox<>(arenaObservableList);
        arenaDropDown.setMinWidth(100);
        arenaDropDown.setMinHeight(30);
        arenaDropDown.setStyle("-fx-background-color: white; -fx-font-size: 14");
        arenaDropDown.setPromptText("Välj arena...");
        arenaDropDown.setStyle("-fx-max-width: 300px;");
        ArenaDAO arenaDAO = new ArenaDAO();

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


        // LOGIK
        // Skapa en dummy-post för "Lägg till ny konsert"
        Concerts newConcertPlaceholder = new Concerts();
        newConcertPlaceholder.setId(0); // 0 indikerar dummy
        newConcertPlaceholder.setArtist_name("- Lägg till ny konsert -");

        // Hämta listan med konserter från databasen
        ConcertDAO concertDAO = new ConcertDAO();
        List<Concerts> concerts = concertDAO.getAllConcerts();
        // Lägg först in dummy-posten
        concertDropDown.getItems().add(newConcertPlaceholder);
        // Lägg därefter in övriga konserter
        concertDropDown.getItems().addAll(concerts);

        updateArenaDropdownForConcerts(arenaDropDown);



        //LOGGA UT - knapp
        Button logoutButton = new Button("Logga ut");
        logoutButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        logoutButton.setMinWidth(100);
        logoutButton.setMinHeight(30);
        logoutButton.setOnAction(event -> {
            FxManager fxManager = new FxManager((Stage) logoutButton.getScene().getWindow());
            fxManager.showLoginScreen();
        });

        //  LÄGG TILL NY
        // lägg till-knapp och logik för CREATEknappen
        Button addButton = new Button("Skapa ny");
        addButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        addButton.setMinWidth(100);
        addButton.setMinHeight(30);
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


                // Lägg till den nya konserten i dropdownen och välj den
                concertDropDown.getItems().add(newConcert);
                concertDropDown.getSelectionModel().select(newConcert);

                // Återställ fälten efter att konserten lagts till
                artistNameField.clear();
                concertDateField.clear();
                concertPriceField.clear();
                concertMinAgeField.clear();
                arenaDropDown.getSelectionModel().clearSelection();
                updateArenaObservableList();
                showAlert("GREAT SUCCESS!", "✅ GREAT SUCCESS! ✅ " +
                        "\nBorat har lagt till konserten i databasen!", Alert.AlertType.INFORMATION);

            } catch (NumberFormatException e) {
                showAlert("Fel", "Pris och åldersgräns måste vara siffror!", Alert.AlertType.ERROR);
            }
        });

        // UPPDATERA
        // lägg till-knapp och logik för UPDATEknappen
        Button updateButton = new Button("Uppdatera");
        updateButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        updateButton.setMinWidth(100);
        updateButton.setMinHeight(30);
        updateButton.setOnAction(event -> {
            Concerts selectedConcert = concertDropDown.getSelectionModel().getSelectedItem();
            if (selectedConcert == null || selectedConcert.getId() == 0) {
                showAlert("Fel", "Välj en giltig konsert att uppdatera!", Alert.AlertType.ERROR);
                return;
            }

            // Hämta fältens värden
            String artistName = artistNameField.getText().trim();
            String selectedArenaName = arenaDropDown.getValue();
            String concertDate = concertDateField.getText().trim();
            String concertPriceText = concertPriceField.getText().trim();
            String concertMinAgeText = concertMinAgeField.getText().trim();

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
                // Sätt om vald konsert för att uppdatera displayen
                concertDropDown.getSelectionModel().select(selectedConcert);

                showAlert("Uppdaterad!", " ✅ GREAT SUCCESS! ✅ \n Borat har uppdaterat konserten för artisten: " + selectedConcert.getArtist_name() +".", Alert.AlertType.INFORMATION);


            } catch (NumberFormatException e) {
                showAlert("Fel", "Pris och åldersgräns måste vara siffror!", Alert.AlertType.ERROR);
            }

        });




        // lägg till-knapp och logik för DELETEknappen
        Button removeButton = new Button("Ta bort");
        removeButton.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        removeButton.setMinWidth(100);
        removeButton.setMinHeight(30);
        removeButton.setOnAction(event -> {
            Concerts selectedConcert = concertDropDown.getSelectionModel().getSelectedItem();

            // Kolla att en artist är valt
            if (selectedConcert == null || selectedConcert.getId() == 0) {
                showAlert("Fel", "Välj en konsert att ta bort!", Alert.AlertType.ERROR);
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
                    concertDropDown.getItems().remove(selectedConcert);
                    // Visa nästa konsert i listan
                    if (!concertDropDown.getItems().isEmpty()) {
                        concertDropDown.getSelectionModel().selectFirst();
                    } else {
                        // Om listan blir tom, lägg tillbaka dummy-posten
                        concertDropDown.getItems().add(newConcertPlaceholder);
                        concertDropDown.getSelectionModel().select(newConcertPlaceholder);
                    }

                    // Rensa textfälten
                    artistNameField.clear();
                    concertDateField.clear();
                    concertPriceField.clear();
                    concertMinAgeField.clear();
                    arenaDropDown.getSelectionModel().clearSelection();
                    concertDropDown.getSelectionModel().clearSelection();

                    showAlert("Borttagen!", "✅ Borat kröp in i databasen och slet ut artisten " + selectedConcert.getArtist_name()
                            + " ✅\nPOFF, GONE!!", Alert.AlertType.INFORMATION);
                }
            });

        });







        // Uppdatera fälten efter vald konsert
        concertDropDown.setOnAction(event -> {
            Concerts selectedConcert = concertDropDown.getSelectionModel().getSelectedItem();
            if (selectedConcert == null) {
                return;
            }
            if (selectedConcert.getId() == 0) { // Dummy-post: rensa fälten
                artistNameField.clear();
                concertDateField.clear();
                concertPriceField.clear();
                concertMinAgeField.clear();
                arenaDropDown.getSelectionModel().clearSelection();
            } else {
                artistNameField.setText(selectedConcert.getArtist_name());
                concertDateField.setText(selectedConcert.getDate());
                concertPriceField.setText(String.valueOf(selectedConcert.getTicket_price()));
                concertMinAgeField.setText(String.valueOf(selectedConcert.getAge_limit()));
                if (selectedConcert.getArena() != null) {
                    arenaDropDown.setValue(selectedConcert.getArena().getName());
                }
            }
        });



        Label headerLabel = new Label("Wigell Conserter - Concerts 🎤");
        headerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Lägg till stuff i vbox1
        hbox3.getChildren().addAll(addButton, updateButton, removeButton);
        vbox.getChildren().addAll(headerLabel, concertDropDown, arenaDropDown,
                artistNameField,  concertDateField, concertPriceField,
                concertMinAgeField, hbox3);
        hbox2.getChildren().addAll(logoutButton);
        root.getChildren().addAll(hbox2,vbox);
        return root;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    //////////////////////////////////////      ALERT POP-UP     //////////////////////////////////////
    // Metod för att Alert error - vi kan göra något annat om vi vill
    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public List updateArenaDropdown(ComboBox arenaDropDown) {
        arenaDropDown.getItems().clear(); // Töm gamla värden
        arenaDropDown.getItems().add("- Lägg till ny arena -"); // Lägg till default-val

        ArenaDAO arenaDAO = new ArenaDAO();
        List<Arena> arenas = arenaDAO.getAllArenas(); // Hämta nya listan från DB
        for (Arena a : arenas) {
            arenaDropDown.getItems().add(a.getName()); // Lägg till arenans namn
        }
        return arenas;
    }

    public List updateArenaDropdownForConcerts(ComboBox arenaDropDown) {
        arenaDropDown.getItems().clear(); // Töm gamla värden

        ArenaDAO arenaDAO = new ArenaDAO();
        List<Arena> arenas = arenaDAO.getAllArenas(); // Hämta nya listan från DB
        for (Arena a : arenas) {
            arenaDropDown.getItems().add(a.getName()); // Lägg till arenans namn
        }
        return arenas;
    }

    public void updateArenaObservableList() {
        arenaObservableList.clear();
        ArenaDAO arenaDAO = new ArenaDAO();
        List<Arena> arenas = arenaDAO.getAllArenas();
        for (Arena a : arenas) {
            arenaObservableList.add(a.getName());
        }
    }


}