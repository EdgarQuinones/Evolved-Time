package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles the add task window, which allows
 * the user to make tasks with specified criteria
 */
public class AddTaskController {

    private MainController mainController;

    private Stage algorithmStage;

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

    @FXML
    private Button algorithmExplainedButton;

    /**
     * Called when the add task window is first opened,
     * used mostly for initializing some objects
     */
    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        algorithmExplainedButton.setShape(new Circle(1.5));

    }

    /**
     * Passes the main window controller to the add task controller, which
     * will be used for multiple methods
     * @param controller Main window controller
     */
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * Calculates the score for the task
     * @return The task score
     */
    public double calcScore() {
        return (difficultySlider.getValue() * (1.5 * timeCommitmentSlider.getValue())) / personalInterestSlider.getValue();
    }

    /**
     * Closes the window, whether after making a task,
     * or to cancel adding the task
     */
    @FXML
    void closeWindow() {
        Stage stage = (Stage) closeWindowButton.getScene().getWindow();
        stage.close();
    }

    /**
     * A user tasks is added to the window, this
     * involves setting the configuration of the components,
     * and then logging it into the csv
     * @param event Event called by button being pressed
     */
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
//                            System.out.println("Size of tasks: " + tasksViewer.getChildren().size());
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
//                System.out.println("Checkbox licked");

                mainController.logBool(checkBox.getText());

                checkBox.getStylesheets().addAll(Objects.requireNonNull(resource).toExternalForm());
                checkBox.setDisable(true);

            });

            button.setOnAction(actionEvent -> {
//                System.out.println("Button Pressed");

                int indexLocation = removeTaskBar.indexOf(button);
                CheckBox deletedTextBox = (CheckBox) ((FlowPane) tasksViewer.getChildren().get(indexLocation)).getChildren().get(0);

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

    /**
     * Window that explains how the algorithm for the score works
     */
    @FXML
    void algorithmExplained() {
        try {
            // If the "Add Task" window is already open, close it
            if (algorithmStage != null && algorithmStage.isShowing()) {
                algorithmStage.close();
            }

            // Load the Add Task scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("algorithm.fxml"));
            Parent root = fxmlLoader.load();

            // Create and show the new Add Task window
            algorithmStage = new Stage();  // Store the stage reference
            algorithmStage.setTitle("How does the algorithm work?");
            algorithmStage.setScene(new Scene(root));
            algorithmStage.setResizable(false);
            algorithmStage.show();

        } catch (Exception e) {
            System.out.println("Can't load new window: " + e.getMessage());
        }
    }

}
