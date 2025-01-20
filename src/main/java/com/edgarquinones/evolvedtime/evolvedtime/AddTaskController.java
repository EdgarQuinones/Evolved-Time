/**
 * Sample Skeleton for 'AddTask.fxml' Controller Class
 */

package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddTaskController {

    private MainController mainController;

    @FXML
    private Button closeWindowButton;

    @FXML
    private Slider difficulty;

    @FXML
    private TextField nameOfTask;

    @FXML
    private Slider personalInterest;

    @FXML
    private Slider timeCommitment;

    // This method allows you to pass the mainController to this controller
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    void addTask(ActionEvent event) throws IOException {
        if (nameOfTask.getText().isEmpty()) return;

        CheckBox checkBox = new CheckBox();
        checkBox.setText(nameOfTask.getText());
        checkBox.setStyle("-fx-font: 24 arial;");

        double score = getScore();

        // Add the checkbox directly to the tasksViewer in the already loaded MainController
        if (mainController != null) {

            VBox tasksViewer = mainController.getTasksViewer();
            ArrayList<Double> scores = mainController.getScores();

            if (!scores.isEmpty()) {
                for (int i = 0; i < scores.size(); i++) {
                    System.out.println("Score Calculated: "+score);
                    System.out.println("Score {"+i+"}: "+scores.get(i));

                    if (score > scores.get(i)) {
                        tasksViewer.getChildren().add(i, checkBox);
                        scores.add(i, score);
                        break;
                    }
                }
                tasksViewer.getChildren().add(checkBox);
                scores.add(score);
            } else {
                tasksViewer.getChildren().add(checkBox);
                scores.add(score);
            }

        }

        // Close the current window (optional)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    private double getScore() {
        return (difficulty.getValue() * (1.5 * timeCommitment.getValue())) / personalInterest.getValue();
    }


    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeWindowButton.getScene().getWindow();
        stage.close();
    }

}
