package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tasks");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        /*
          TODO:
          - Add persistence (log file)
          - Prevent scaling
          - Menu Bar
          - Be able to remove tasks
          - read time management book
         */
    }
}