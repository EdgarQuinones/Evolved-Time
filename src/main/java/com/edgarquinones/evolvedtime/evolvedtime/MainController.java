/**
 * Sample Skeleton for 'Main.fxml' Controller Class
 */

package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class MainController {

    public static String tasksFileName = "tasks.csv";

    private Stage addTaskStage;

    @FXML
    private VBox removeTaskBar;

    @FXML
    private Text dateText;

    @FXML
    private VBox tasksViewer;
    private ArrayList<Task> tasks;

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
            System.out.println("Can't load new window: " + e.getMessage());
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        dateText.setText(getDate());
        dateText.setStyle("-fx-underline: true ;");

        tasks = new ArrayList<>();
        tasksViewer.setSpacing(5);
        getTasksCSV();

    }

    public void getTasksCSV() {
        try {
            File file = new File(tasksFileName);
            Scanner fileScnr = new Scanner(file);
            Scanner lineScnr = null;

            fileScnr.nextLine(); // Skip header


            while (fileScnr.hasNextLine()) {
                String line = fileScnr.nextLine();
                lineScnr = new Scanner(line);
                lineScnr.useDelimiter(",");

                CheckBox checkBox = new CheckBox(lineScnr.next());
                double score = Double.parseDouble(lineScnr.next());
                boolean isChecked = Boolean.parseBoolean(lineScnr.next());

                Task temp = new Task(checkBox, score, isChecked);

                addFileTask(temp);
            }

            assert lineScnr != null;
            lineScnr.close();
            fileScnr.close();

        } catch (FileNotFoundException | NoSuchElementException e) {
            try {
                FileWriter fileWriter = new FileWriter(tasksFileName, true);
                PrintWriter out = new PrintWriter(fileWriter);

                out.println("nameOfTask,score,isChecked");
                out.close();

            } catch (IOException ex) {
                System.out.println("You do not have permission to write/read: " + ex.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with addTask() method: " + e.getMessage());
        }


    }

    public VBox getTasksViewer() {
        return tasksViewer;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public VBox getRemoveTaskBar() {
        return removeTaskBar;
    }

    public void logTask(Task task) {
        System.out.println("Task logged");
        try {
            FileWriter fileWriter = new FileWriter(tasksFileName, true);
            PrintWriter out = new PrintWriter(fileWriter);

            out.printf("%s,%.02f,%b\n", task.getCheckBox().getText(), task.getScore(), task.isChecked());
            out.close();

        } catch (IOException ex) {
            System.out.println("You do not have permission to write/read: " + ex.getMessage());
        }
    }

    public void removeLog(String taskName) {
        System.out.println("Remove log called");
        try {
            File file = new File(tasksFileName);
            Scanner fileScnr = new Scanner(file);
            Scanner lineScnr = null;
            FileWriter fileWriter = new FileWriter(tasksFileName, false);
            PrintWriter out = new PrintWriter(fileWriter);

            out.println("nameOfTask,score,isChecked");

            while (fileScnr.hasNextLine()) {

                // TODO: Come up with method to prevent repeat code #1
                String line = fileScnr.nextLine();
                lineScnr = new Scanner(line);
                lineScnr.useDelimiter(",");

                CheckBox checkBox = new CheckBox(lineScnr.next());
                double score = Double.parseDouble(lineScnr.next());
                boolean isChecked = Boolean.parseBoolean(lineScnr.next());

                Task task = new Task(checkBox, score, isChecked);

                System.out.printf("input text: %s, current text: %s", taskName, checkBox.getText());
                if (!taskName.equals(checkBox.getText())) {
                    out.printf("%s,%.02f,%b\n", task.getCheckBox().getText(), task.getScore(), task.isChecked());

                }

            }

            assert lineScnr != null;
            lineScnr.close();
            fileScnr.close();
            out.close();

        } catch (Exception ignored) {
        }
    }
        
    public void logBool(String taskName) {
        System.out.println("Change Boolean Called");
        try {

            StringBuilder stringBuilder = new StringBuilder();
            File file = new File(tasksFileName);
            Scanner fileScnr = new Scanner(file);
            Scanner lineScnr = null;
            FileWriter fileWriter = new FileWriter(tasksFileName, true);
            PrintWriter out = new PrintWriter(fileWriter);

            stringBuilder.append("nameOfTask,score,isChecked");

            while (fileScnr.hasNextLine()) {

                // TODO: Come up with method to prevent repeat code #1
                String line = fileScnr.nextLine();
                lineScnr = new Scanner(line);
                lineScnr.useDelimiter(",");

                CheckBox checkBox = new CheckBox(lineScnr.next());
                double score = Double.parseDouble(lineScnr.next());
                boolean isChecked = Boolean.parseBoolean(lineScnr.next());

                Task task = new Task(checkBox, score, isChecked);

                System.out.printf("input text: %s, current text: %s", taskName, checkBox.getText());
                if (taskName.equals(checkBox.getText())) {
                    stringBuilder.append(String.format("%s,%.02f,%b\n", task.getCheckBox().getText(), task.getScore(), task.isChecked()));


                } else {
                    stringBuilder.append(String.format("%s,%.02f,%b\n", task.getCheckBox().getText(), task.getScore(), !task.isChecked()));

                }

            }

            out.print(stringBuilder);

            assert lineScnr != null;
            lineScnr.close();
            fileScnr.close();
            out.close();

        } catch (Exception ignored) {
        }
    }

    @FXML
    void addFileTask(Task task) throws IOException {

        if (task.getCheckBox().getText().isEmpty()) return;

        CheckBox checkBox = task.getCheckBox();
        checkBox.setStyle("-fx-font: 24 arial;");

        Button button = new Button("x");

        double score = task.getScore();

        // TODO: Come up with method to prevent repeat code #2
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {

                if (score > tasks.get(i).getScore()) {
                    tasksViewer.getChildren().add(i, checkBox);
                    removeTaskBar.getChildren().add(i, button);
                    tasks.add(i, task);
                    break;
                }
            }
            try {
                tasksViewer.getChildren().add(checkBox);
                removeTaskBar.getChildren().add(button);
                tasks.add(task);
            } catch (IllegalArgumentException ignored) {
            }

        } else {
            tasksViewer.getChildren().add(checkBox);
            tasks.add(task);
            removeTaskBar.getChildren().add(button);
        }

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean isClicked) {
                checkBox.getStylesheets().addAll(Objects.requireNonNull(getClass().getResource("strikethrough.css")).toExternalForm());
                checkBox.setDisable(true);
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int indexLocation = removeTaskBar.getChildren().indexOf(button);
                tasksViewer.getChildren().remove(indexLocation);
                removeTaskBar.getChildren().remove(indexLocation);
            }
        });

    }

}
