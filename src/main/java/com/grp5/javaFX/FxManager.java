package com.grp5.javaFX;

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

        // Skapa en TabPane och visa inloggnings- och registreringsflikarna
        tabPane = new TabPane();
        root.setCenter(tabPane);
        showLoginScreen();
    }

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

    public void showWcScreen() {
        tabPane.getTabs().clear(); // Rensa gamla flikar

        // Skapa WC-skärmen med dess tre flikar (WC, Arena, Konsert)
        WcScreen wcScreen = new WcScreen();
        tabPane.getTabs().addAll(wcScreen.getTabPane().getTabs());
    }
}
