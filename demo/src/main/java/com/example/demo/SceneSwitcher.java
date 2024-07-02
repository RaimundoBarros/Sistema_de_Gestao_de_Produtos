package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

    public static void switchScene(Stage stage, String fxmlFile) throws IOException {
        URL fxmlLocation = SceneSwitcher.class.getResource(fxmlFile);
        if (fxmlLocation == null) {
            throw new IOException("FXML file not found: " + fxmlFile);
        }
        Parent root = FXMLLoader.load(fxmlLocation);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
