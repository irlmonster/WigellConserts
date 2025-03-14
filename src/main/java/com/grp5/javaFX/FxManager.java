package com.grp5.javaFX;

import com.grp5.entitys.Customer;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FxManager {
    private BorderPane root;
    private Scene scene;
    private Stage stage;
    private TabPane tabPane;

    public FxManager(Stage stage) {
        this.stage = stage;
        root = new BorderPane();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Wigell Conserter");
        stage.show();




        tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #4682B4;");
        root.setCenter(tabPane);

        tabPane.setStyle("-fx-tab-min-width: 364px; -fx-tab-max-width: 365px;"); // Storlek på flikar

        // **Lyssnare för att ändra färg när fliken ändras**
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            for (Tab tab : tabPane.getTabs()) {
                tab.setStyle("-fx-background-color: #4682B4; -fx-min-width: 150px; -fx-max-width: 200px;");
            }
            if (newTab != null) {
                newTab.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: white; -fx-min-width: 150px; -fx-max-width: 200px;");
            }
        });
        showLoginScreen();
    }
    //////////////////////////////////////      LOGIN SCREEN     //////////////////////////////////////
    public void showLoginScreen() {
        tabPane.getTabs().clear(); // Rensa alla gamla flikar

        // Skapa inloggningsflik
        LoginTab loginScreen = new LoginTab(this);
        Tab loginTab = loginScreen.getTab();
        loginTab.setClosable(false);

        // Skapa registreringsflik
        RegNewUserTab regNewUserTab = new RegNewUserTab(this);
        Tab regTab = regNewUserTab.getTab();
        regTab.setClosable(false);

        // Lägg till båda flikarna i `TabPane`
        tabPane.getTabs().addAll(loginTab, regTab);
    }
    //////////////////////////////////////      WC SCREEN     //////////////////////////////////////
    public void showWcScreen() {
        tabPane.getTabs().clear(); // Rensa gamla flikar
        tabPane.setStyle("-fx-background-color: #4682B4;");
        // Skapa WC-skärmen med dess tre flikar (WC, Arena, Konsert)
        WcScreen wcScreen = new WcScreen();
        tabPane.getTabs().addAll(wcScreen.getTabPane().getTabs());

        // **Sätt min/max bredd på flikarna**
        tabPane.setStyle("-fx-tab-min-width: 245px; -fx-tab-max-width: 246px;");

        // **Lyssnare för att ändra färg när fliken ändras**
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            for (Tab tab : tabPane.getTabs()) {
                tab.setStyle("-fx-background-color: #4682B4; -fx-min-width: 150px; -fx-max-width: 200px;");
            }
            if (newTab != null) {
                newTab.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: white; -fx-min-width: 150px; -fx-max-width: 200px;");
            }
        });
    }

    public void showCustomerScreen(String username){
        tabPane.getTabs().clear(); // Rensa gamla flikar
        tabPane.setStyle("-fx-background-color: #4682B4;");
        // Skapa CustomerScreen med två flikar (Bokning, Inställningar)
        CustomerScreen customerScreen = new CustomerScreen(username);
        tabPane.getTabs().addAll(customerScreen.getTabPane().getTabs());

        // **Sätt min/max bredd på flikarna**
        tabPane.setStyle("-fx-tab-min-width: 245px; -fx-tab-max-width: 246px;");

        // **Lyssnare för att ändra färg när fliken ändras**
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            for (Tab tab : tabPane.getTabs()) {
                tab.setStyle("-fx-background-color: #4682B4; -fx-min-width: 150px; -fx-max-width: 200px;");
            }
            if (newTab != null) {
                newTab.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: white; -fx-min-width: 150px; -fx-max-width: 200px;");
            }
        });
    }


}
