package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Window that describes the algorithm
 */
public class AlgorithmController {

    @FXML
    private Button returnButton;

    /**
     * Closes the algorithm window
     */
    @FXML
    void closeWindow() {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens up the projects GitHub on the user's browser
     */
    @FXML
    void openGithub() {
        try {
            Desktop.getDesktop().browse(new URL(MainController.GITHUB_URL).toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error reaching website");
        }
    }

}
