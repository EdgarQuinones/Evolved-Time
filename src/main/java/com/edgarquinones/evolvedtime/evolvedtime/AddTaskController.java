/**
 * Sample Skeleton for 'AddTask.fxml' Controller Class
 */

package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class AddTaskController {

    private MainController mainController;

    @FXML
    private Button closeWindowButton;

    @FXML
    private Slider difficultySlider;

    @FXML
    private TextField nameOfTask;

    @FXML
    private Slider personalInterestSlider;

    @FXML
    private Slider timeCommitmentSlider;

    // This method allows you to pass the mainController to this controller
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }


    public double calcScore() {
        return (difficultySlider.getValue() * (1.5 * timeCommitmentSlider.getValue())) / personalInterestSlider.getValue();
    }


    @FXML
    void closeWindow() {
        Stage stage = (Stage) closeWindowButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addTask(ActionEvent event) {

        if (nameOfTask.getText().isEmpty()) return;

        CheckBox checkBox = new CheckBox();
        checkBox.setText(nameOfTask.getText());
        checkBox.setStyle("-fx-font: 24 arial;");
        checkBox.setWrapText(true);
        checkBox.setPrefWidth(MainController.CHECKBOX_WIDTH);


        Button button = new Button("x");

        double score = calcScore();

        Task task = new Task(checkBox, score, false);
        task.setScoreStats(new Score(personalInterestSlider.getValue(), timeCommitmentSlider.getValue(), difficultySlider.getValue()));

        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(MainController.FLOWPANE_HEIGHT);
        flowPane.setAlignment(Pos.CENTER_LEFT);

        flowPane.getChildren().addAll(checkBox, button);

        if (mainController != null) {
            VBox tasksViewer = mainController.getTasksViewer();
            ArrayList<Task> tasks = mainController.getTasks();
            ArrayList<Button> removeTaskBar = mainController.getRemoveTaskBar();
            URL resource = getClass().getResource("strikethrough.css");

            if (!tasks.isEmpty()) {
                boolean taskAdded = false;
                for (int i = 0; i < tasks.size(); i++) {
                    if (score > tasks.get(i).getScore()) {
                        try {
                            System.out.println("Size of tasks: " + tasksViewer.getChildren().size());
                            tasksViewer.getChildren().add(i, flowPane);
                            removeTaskBar.add(i, button);
                            tasks.add(i, task);
                            taskAdded = true;
                            break;
                        } catch (IndexOutOfBoundsException e) {
                            tasksViewer.getChildren().add(flowPane);
                            tasks.add(task);
                            removeTaskBar.add(button);
                            taskAdded = true;
                            break;
                        }
                    }
                }
                if (!taskAdded) {
                    tasksViewer.getChildren().add(flowPane);
                    removeTaskBar.add(button);
                    tasks.add(task);
                }
            } else {
                tasksViewer.getChildren().add(flowPane);
                tasks.add(task);
                removeTaskBar.add(button);
            }

            checkBox.selectedProperty().addListener((observableValue, oldValue, isClicked) -> {
                System.out.println("Checkbox licked");

                mainController.logBool(checkBox.getText());

                checkBox.getStylesheets().addAll(Objects.requireNonNull(resource).toExternalForm());
                checkBox.setDisable(true);

            });

            button.setOnAction(actionEvent -> {
                System.out.println("Button Pressed");

                int indexLocation = removeTaskBar.indexOf(button);
                CheckBox deletedTextBox = (CheckBox) tasksViewer.getChildren().get(indexLocation);

                mainController.removeLog(deletedTextBox.getText());
                tasksViewer.getChildren().remove(indexLocation);
                removeTaskBar.remove(indexLocation);

            });

            mainController.logTask(task);
        }

        if (event != null) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
    }


}
