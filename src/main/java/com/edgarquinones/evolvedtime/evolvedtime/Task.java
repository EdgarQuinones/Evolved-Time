package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.scene.control.CheckBox;

public class Task {

    private CheckBox checkBox;
    private double score;
    private boolean isChecked;

    public Task(CheckBox checkBox, double score, boolean isChecked) {
        this.checkBox = checkBox;
        this.score = score;
        this.isChecked = isChecked;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return String.format("%s,%.02f,%b\n", checkBox.getText(), score, isChecked);
    }
}
