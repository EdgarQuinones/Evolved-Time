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
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class MainController {

    public static String tasksFileName = "tasks.csv";
    public static ArrayList<Task> csvContents;
    public static boolean newFile;

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
    void openAddTaskStage(ActionEvent event) {
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

        csvContents = new ArrayList<>();

        tasks = new ArrayList<>();
        getTasksCSV();

    }

    public void shutdown() {
        System.out.println("Program Closed");
        updateCSV();
    }

    private void updateCSV() {

        try {
            FileWriter file = new FileWriter(tasksFileName, false);
            PrintWriter out = new PrintWriter(file);

            out.println("nameOfTask,score,isChecked");

            for (Task task : csvContents) {

                out.printf(task.toString());
            }

            out.close();

        } catch (IOException e) {
            System.out.println("You do not have reading/writing permissions");
        }

    }

    public void getTasksCSV() {
        try {
            File file = new File(tasksFileName);
            Scanner fileScnr = new Scanner(file);
            Scanner lineScnr = null;

            if (!fileScnr.nextLine().equals("nameOfTask,score,isChecked")) throw new NoSuchElementException();


            while (fileScnr.hasNextLine()) {
                String line = fileScnr.nextLine();
                lineScnr = new Scanner(line);
                lineScnr.useDelimiter(",");

                CheckBox checkBox = new CheckBox(lineScnr.next());
                double score = Double.parseDouble(lineScnr.next());
                boolean isChecked = Boolean.parseBoolean(lineScnr.next());

                if (isChecked) {
                    checkBox.setSelected(true);
                    checkBox.setDisable(true);
                }

                Task temp = new Task(checkBox, score, isChecked);

                csvContents.add(temp);
                addFileTask(temp);

            }

            if (lineScnr != null) lineScnr.close();
            fileScnr.close();

        } catch (FileNotFoundException | NoSuchElementException e) {
            newFile = true;
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
        csvContents.add(task);
    }

    public void removeLog(String taskName) {
        System.out.println("Remove log called");

        for (int i = 0; i < csvContents.size(); i++) {
            String currentText = csvContents.get(i).getCheckBox().getText();
            if (currentText.equals(taskName)) {
                csvContents.remove(i);
                break;
            }
        }

    }

    public void logBool(String taskName) {
        System.out.println("Change Boolean Called");

        for (Task csvContent : csvContents) {
            String currentText = csvContent.getCheckBox().getText();
            if (currentText.equals(taskName)) {
                csvContent.setChecked(true);
                break;
            }
        }

    }

    @FXML
    void addFileTask(Task task) throws IOException {
        System.out.println("Task added to file");

        URL resource = getClass().getResource("strikethrough.css");

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
                System.out.println("Checkbox licked");

                checkBox.getStylesheets().addAll(Objects.requireNonNull(resource).toExternalForm());
                checkBox.setDisable(true);


            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Button Pressed");

                int indexLocation = removeTaskBar.getChildren().indexOf(button);
                CheckBox deletedTextBox = (CheckBox) tasksViewer.getChildren().get(indexLocation);

                removeLog(deletedTextBox.getText());
                tasksViewer.getChildren().remove(deletedTextBox);
                removeTaskBar.getChildren().remove(indexLocation);

            }
        });

    }

}
