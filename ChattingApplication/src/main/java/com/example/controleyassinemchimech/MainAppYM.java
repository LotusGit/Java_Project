package com.example.controleyassinemchimech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainAppYM extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppYM.class.getResource("Scene1YM.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Manger's App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}