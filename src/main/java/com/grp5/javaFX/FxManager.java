package com.grp5.javaFX;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FxManager {
    private BorderPane root;
    private Scene scene;
    private Stage stage;

    public FxManager(Stage stage) {
        this.stage = stage;
        // Skapa en BorderPane som huvudcontainer
        root = new BorderPane();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Wigell Conserter");
        stage.show();

        // Visa inloggningssidan först
        showLoginScreen();
    }

    public void showLoginScreen() {
        // Skapa inloggningsskärmen och passera FxManager som referens
        LoginTab loginScreen = new LoginTab(this);
        // Sätt inloggningssidan i mitten av BorderPane
        root.setCenter(loginScreen.getContent());
    }

    public void showWcScreen() {
        // Skapa WC-sidan
        WcScreen wcScreen = new WcScreen();
        // Sätt WC-sidan i mitten av BorderPane
        root.setCenter(wcScreen.getRoot());
    }
}

