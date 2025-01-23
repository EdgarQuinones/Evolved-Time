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

/**
 * Handles the main window of the project.
 * This includes:
 *  checkbox
 *  buttons
 *  etc...
 */
public class MainController {

    public static final String GITHUB_URL = "https://github.com/EdgarQuinones/Evolved-Time";

    public static final int FLOWPANE_HEIGHT = 0;
    public static final int CHECKBOX_WIDTH = 550;


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

    /**
     * Sets the data text label as the current
     * day of the week.
     * @return day of the week, month, day of the month
     */
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

    /**
     * Gets the Task based on the user input
     * @param line Name of the task
     * @return The task object
     */
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

    /**
     * Called when the main window is first opened,
     * used mostly for initializing some objects
     */
    @FXML
    void initialize() {
        dateText.setText(getDate());

        csvContents = new ArrayList<>();
        removeTaskBar = new ArrayList<>();
        tasks = new ArrayList<>();
        getTasksCSV();

    }

    /**
     * Opens the window that handles adding a task.
     * This window has the user enter the task name,
     * as well as all the criteria involved in each task.
     */
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

    /**
     * Writes the csv file required for persistence,
     * and closes the program.
     */
    public void shutdown() {
        System.out.println("Program Closed");
        updateCSV();
        Platform.exit();
    }

    /**
     * Handles the writing of the tasks into a csv file.
     * An arraylist is used to make changes without
     * read/writing to the file more than once.
     */
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

    /**
     * When the program is first run,
     * this method is run and reads the csv file
     * and adds them to the window.
     */
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
        }
    }

    /**
     * Gets the VBox that holds all the tasks,
     * and is used to add/remove tasks
     * @return The VBox containing all the tasks
     */
    public VBox getTasksViewer() {
        return tasksViewer;
    }

    /**
     * Returns the arraylist containing all the tasks
     * @return Arraylist of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the arraylist of the buttons in the program.
     * These buttons are used to remove the checkboxes/tasks
     * @return Arraylist of buttons
     */
    public ArrayList<Button> getRemoveTaskBar() {
        return removeTaskBar;
    }

    /**
     * Called everytime addTask or addFileTask is called
     * to log the task into the csv file.
     * @param task Task user just added
     */
    public void logTask(Task task) {
        System.out.println("Task logged");
        csvContents.add(task);
    }

    /**
     * Called when a user removes a task
     * @param taskName Name of the task that is used
     * when removing them from the arraylist that
     * will be removed from the csv file
     */
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

    /**
     * Called when a user adds a task
     * @param taskName Name of the task that is used
     * when add them from the arraylist that
     * will be added from the csv file
     */
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

    /**
     * A user tasks is added to the window, this
     * involves setting the configuration of the components,
     * and then logging it into the csv
     * @param task The task being added
     */
    @FXML
    void addFileTask(Task task) {
        System.out.println("Task added to file");

        URL resource = getClass().getResource("strikethrough.css");

        if (task.getCheckBox().getText().isEmpty()) return;

        CheckBox checkBox = task.getCheckBox();
        checkBox.setStyle("-fx-font: 24 arial;");
        checkBox.setWrapText(true);
        checkBox.setPrefWidth(CHECKBOX_WIDTH);

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

    /**
     * Opens up the projects GitHub on the user's browser
     */
    @FXML
    void openGithub() {
        try {
            Desktop.getDesktop().browse(new URL(GITHUB_URL).toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error reaching website");
        }
    }

    /**
     * Opens up the users browser and allows them to
     * report any bugs they may find on the program
     */
    @FXML
    void reportAnIssue() {
        try {
            Desktop.getDesktop().browse(new URL(GITHUB_URL+"/issues").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error reaching website");
        }
    }

    /**
     * Removes all the tasks currently
     * on the program
     */
    @FXML
    void clearList() {
        removeTaskBar.clear();
        tasksViewer.getChildren().clear();
        tasks.clear();
        csvContents.clear();
    }

}
