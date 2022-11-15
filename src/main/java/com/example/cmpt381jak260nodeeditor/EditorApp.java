package com.example.cmpt381jak260nodeeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        MainUI mainui = new MainUI(stage);
        Scene scene = new Scene(mainui);

        stage.setTitle("State Machine Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}