package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController mainController = fxmlLoader.getController();
        stage.setTitle("Tasks");
        stage.setScene(scene);
        stage.setOnHidden(e -> mainController.shutdown());
        stage.setResizable(false);
        stage.show();
    }

}