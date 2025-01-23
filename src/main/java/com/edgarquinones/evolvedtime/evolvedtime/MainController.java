package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

@SuppressWarnings("CommentedOutCode") // For sort list method
public class MainController {

    public static final int FLOWPANE_HEIGHT = 0;
    public static final int CHECKBOX_WIDTH = 515;


    public static String tasksFileName = "tasks.csv";
    public static ArrayList<Task> csvContents;
    public static boolean newFile;

    private Stage addTaskStage;

    @FXML
    private ArrayList<Button> removeTaskBar;

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

    private static Task getTask(String line) {
        String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        CheckBox checkBox = new CheckBox(tokens[0].replaceAll("\"", ""));
        checkBox.setPrefWidth(CHECKBOX_WIDTH);

        double score = Double.parseDouble(tokens[1]);
        boolean isChecked = Boolean.parseBoolean(tokens[2]);

        Task temp = new Task(checkBox, score, isChecked);

        if (temp.isChecked()) {
            checkBox.setSelected(true);
            checkBox.setDisable(true);
        }
        return temp;
    }

    @FXML
    void openAddTaskStage() {
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

        csvContents = new ArrayList<>();
        removeTaskBar = new ArrayList<>();
        tasks = new ArrayList<>();
        getTasksCSV();

    }

    public void shutdown() {
        System.out.println("Program Closed");
        updateCSV();
        Platform.exit();
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

            if (!fileScnr.nextLine().equals("nameOfTask,score,isChecked")) throw new NoSuchElementException();


            while (fileScnr.hasNextLine()) {
                String line = fileScnr.nextLine();

                // Allows commas inside quotes
                Task temp = getTask(line);


                csvContents.add(temp);
                addFileTask(temp);

            }

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

    public ArrayList<Button> getRemoveTaskBar() {
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
        checkBox.setWrapText(true);
        checkBox.setPrefWidth(515);

        Button button = new Button("x");

        double score = task.getScore();

        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(FLOWPANE_HEIGHT);
        flowPane.setAlignment(Pos.CENTER_LEFT);
        flowPane.getChildren().addAll(checkBox, button);

        // TODO: Come up with method to prevent repeat code #2
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {

                if (score > tasks.get(i).getScore()) {
                    //instead of tasksViewer adding checkbox,
                    // it adds a FlowPane containing a checkbox and button

                    tasksViewer.getChildren().add(i, flowPane);
                    removeTaskBar.add(i, button);
                    tasks.add(i, task);
                    break;
                }
            }
            try {
                tasksViewer.getChildren().add(flowPane);
                removeTaskBar.add(button);
                tasks.add(task);
            } catch (IllegalArgumentException ignored) {
            }

        } else {
            tasksViewer.getChildren().add(flowPane);
            tasks.add(task);
            removeTaskBar.add(button);
        }

        checkBox.selectedProperty().addListener((observableValue, oldValue, isClicked) -> {
            System.out.println("Checkbox licked");

            checkBox.getStylesheets().addAll(Objects.requireNonNull(resource).toExternalForm());
            checkBox.setDisable(true);


        });

        button.setOnAction(actionEvent -> {
            System.out.println("Button Pressed");

            int indexLocation = removeTaskBar.indexOf(button);
            CheckBox deletedTextBox = (CheckBox) ((FlowPane) tasksViewer.getChildren().get(indexLocation)).getChildren().get(0);

            removeLog(deletedTextBox.getText());
            tasksViewer.getChildren().remove(indexLocation);
            removeTaskBar.remove(indexLocation);

        });

    }

    @FXML
    void openGithub() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/EdgarQuinones/Evolved-Time").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error reaching website");
        }
    }

    @FXML
    void reportAnIssue() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/EdgarQuinones/Evolved-Time/issues").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error reaching website");
        }
    }

    @FXML
    void clearList() {
        removeTaskBar.clear();
        tasksViewer.getChildren().clear();
        tasks.clear();
        csvContents.clear();
    }

    // TODO: sort list
//    @FXML
//    void sortList(ActionEvent event) {
//        String sortOption = ((RadioMenuItem)event.getSource()).getText();
//
//        // Look at i index's <stat>, if bigger, go up
//
//        switch (sortOption) {
//            case "Personal Interest" :
//                System.out.println("personal button pressed");
//
//                for (int i = 0; i < tasks.size(); i++) {
//
//                    double currentPersonalInterest = tasks.get(i).getScoreStats().getPersonalInterest();
//                    for (int j = 0; i < tasks.size(); j++) {
//
//                        double tempPersonalInterest = tasks.get(j).getScoreStats().getPersonalInterest();
//
//                        if (currentPersonalInterest > tempPersonalInterest ) {
//                            return;
//                            // swap VBOX of buttons == removeTaskBar
//                            // swap VBOX of tasks == tasksViewer
//                            // swap tasks array += tasks
//                        }
//
//                    }
//
//                }
//
//            break;
//            case "Difficulty" :
//                System.out.println("Diff button pressed");
//                break;
//            case "Time Commitment" :
//                System.out.println("Time button pressed");
//                break;
//            default :
//                System.out.println("Default button pressed");
//        }
//
//    }

}
