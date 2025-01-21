/**
 * Sample Skeleton for 'Main.fxml' Controller Class
 */

package com.edgarquinones.evolvedtime.evolvedtime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {

    private Stage addTaskStage;

    @FXML
    private VBox removeTaskBar;

    @FXML
    private Text dateText;

    @FXML
    private VBox tasksViewer;
    private ArrayList<Task> tasks;

    @FXML
    void switchToAddTaskScene(ActionEvent event) {
        try {
            // If the "Add Task" window is already open, close it
            if (addTaskStage != null && addTaskStage.isShowing()) {
                addTaskStage.close();
            }

            // Load the Add Task scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTask.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller for the Add Task scene
            AddTaskController addTaskController = fxmlLoader.getController();

            // Pass the current MainController to the Add Task controller
            addTaskController.setMainController(this);

            // Create and show the new Add Task window
            addTaskStage = new Stage();  // Store the stage reference
            addTaskStage.setTitle("Add Task");
            addTaskStage.setScene(new Scene(root));
            addTaskStage.setResizable(false);
            addTaskStage.show();

        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        dateText.setText(getDate());
        dateText.setStyle("-fx-underline: true ;");

        tasks = new ArrayList<>();
        tasksViewer.setSpacing(5);
    }

    public static String getDate() {
        // Sets up the date format
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
        String formattedDate = today.format(formatter);
        int dayOfMonth = today.getDayOfMonth();

        // Determine the correct suffix for the day of the month
        String suffix;
        if (dayOfMonth >= 11 && dayOfMonth <= 13) {
            suffix = "th";  // Special case for 11th, 12th, and 13th
        } else if (dayOfMonth % 10 == 1) {
            suffix = "st";
        } else if (dayOfMonth % 10 == 2) {
            suffix = "nd";
        } else if (dayOfMonth % 10 == 3) {
            suffix = "rd";
        } else {
            suffix = "th";
        }

        // Return the formatted date with the suffix
        return formattedDate + suffix;
    }

    public VBox getTasksViewer() {
        return tasksViewer;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public VBox getRemoveTaskBar() {return removeTaskBar;}


}
