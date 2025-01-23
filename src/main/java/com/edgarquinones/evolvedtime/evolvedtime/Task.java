package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.scene.control.CheckBox;

/**
 * Each task in the app will be stored in a Task object.
 * It contains everything including the score, the checkbox,
 * the Score object, and the status of the checkbox being checked.
 */
public class Task {

    private final CheckBox checkBox;
    private final double score;
    private Score scoreStats;
    private boolean isChecked;

    /**
     * Constructor
     * @param checkBox Checkbox on the task
     * @param score Score calculated from the algorithm
     * @param isChecked Whether the checkbox is checked or not
     */
    public Task(CheckBox checkBox, double score, boolean isChecked) {
        this.checkBox = checkBox;
        this.score = score;
        this.isChecked = isChecked;
    }

    /**
     * Gets the checkbox JavaFX object
     * @return The checkbox
     */
    public CheckBox getCheckBox() {
        return checkBox;
    }

    /**
     * Gets the calculated score
     * @return The score
     */
    public double getScore() {
        return score;
    }

    /**
     * Returns the csv version of the task object
     * @return csv string
     */
    @Override
    public String toString() {
        return String.format("\"%s\",%.02f,%b\n", checkBox.getText(), score, checkBox.isSelected());
    }

    /**
     * Gets the output of Whether the checkbox is checked
     * @return current status of the checkbox
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Sets the new value of the isCheck bool
     * @param checked new value
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    /**
     * Gets the Score object, to be used in the task filter.
     * However, until then will be seen as unused.
     * @return The Score object
     */
    @SuppressWarnings("unused")
    public Score getScoreStats() {
        return scoreStats;
    }

    /**
     * Sets the Score Object as scoreStats
     * @param scoreStats new Score object
     */
    public void setScoreStats(Score scoreStats) {
        this.scoreStats = scoreStats;
    }
}
