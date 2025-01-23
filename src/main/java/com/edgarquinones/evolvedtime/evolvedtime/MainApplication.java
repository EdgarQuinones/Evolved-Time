/*
 * Project: Evolved Time - Task Management App
 * By: Edgar Quinones
 *
 * Description: A task management app that sorts your goals of the day based on a set of criteria.
 * I believe this can help people be more productive.
 */

package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX main class that launches the first scene.
 * @author Edgar Quinones
 * @version 1.0.0
 */
public class MainApplication extends Application {

    /**
     * Main class does not do anything in this project, just starts the program
     * @param args used for command-line arguments, but not needed for this program
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * The actual "main" class for this project. Declares and initializes
     * the main window of the program.
     * @param stage The main window of the program.
     */
    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainController mainController = fxmlLoader.getController();

            String css = Objects.requireNonNull(this.getClass().getResource("mainApp.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Evolved Time 1.0.0");
            stage.setScene(scene);
            stage.setOnHidden(e -> mainController.shutdown());
            stage.setResizable(false);
            stage.show();

        }catch (IOException e) {
            System.out.println("Error finding file");
        }
    }

}