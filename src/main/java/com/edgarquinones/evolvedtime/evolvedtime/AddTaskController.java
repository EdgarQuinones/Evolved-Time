/**
 * Sample Skeleton for 'AddTask.fxml' Controller Class
 */

package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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


    public double getScore() {
        return (difficulty.getValue() * (1.5 * timeCommitment.getValue())) / personalInterest.getValue();
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


        Button button = new Button("x");

        double score = getScore();

        Task task = new Task(checkBox, score, false);

        if (mainController != null) {
            VBox tasksViewer = mainController.getTasksViewer();
            ArrayList<Task> tasks = mainController.getTasks();
            VBox removeTaskBar = mainController.getRemoveTaskBar();
            URL resource = getClass().getResource("strikethrough.css");

            if (!tasks.isEmpty()) {
                boolean taskAdded = false;
                for (int i = 0; i < tasks.size(); i++) {
                    if (score > tasks.get(i).getScore()) {
                        try {
                            System.out.println("Size of tasks: " + tasksViewer.getChildren().size());
                            tasksViewer.getChildren().add(i, checkBox);
                            removeTaskBar.getChildren().add(i, button);
                            tasks.add(i, task);
                            taskAdded = true;
                            break;
                        } catch (IndexOutOfBoundsException e) {
                            tasksViewer.getChildren().add(checkBox);
                            tasks.add(task);
                            removeTaskBar.getChildren().add(button);
                            taskAdded = true;
                            break;
                        }
                    }
                }
                if (!taskAdded) {
                    tasksViewer.getChildren().add(checkBox);
                    removeTaskBar.getChildren().add(button);
                    tasks.add(task);
                }
            } else {
                tasksViewer.getChildren().add(checkBox);
                tasks.add(task);
                removeTaskBar.getChildren().add(button);
            }

            checkBox.selectedProperty().addListener((observableValue, oldValue, isClicked) -> {
                System.out.println("Checkbox licked");

                mainController.logBool(checkBox.getText());

                checkBox.getStylesheets().addAll(Objects.requireNonNull(resource).toExternalForm());
                checkBox.setDisable(true);

            });

            button.setOnAction(actionEvent -> {
                System.out.println("Button Pressed");

                int indexLocation = removeTaskBar.getChildren().indexOf(button);
                CheckBox deletedTextBox = (CheckBox) tasksViewer.getChildren().get(indexLocation);

                mainController.removeLog(deletedTextBox.getText());
                tasksViewer.getChildren().remove(deletedTextBox);
                removeTaskBar.getChildren().remove(indexLocation);

            });

            mainController.logTask(task);
        }

        if (event != null) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
    }


}
