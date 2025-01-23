package com.edgarquinones.evolvedtime.evolvedtime;

import javafx.scene.control.CheckBox;

public class Task {

    private final CheckBox checkBox;
    private final double score;
    private Score scoreStats;
    private boolean isChecked;

    public Task(CheckBox checkBox, double score, boolean isChecked) {
        this.checkBox = checkBox;
        this.score = score;
        this.isChecked = isChecked;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("\"%s\",%.02f,%b\n", checkBox.getText(), score, checkBox.isSelected());
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @SuppressWarnings("unused")
    public Score getScoreStats() {
        return scoreStats;
    }

    public void setScoreStats(Score scoreStats) {
        this.scoreStats = scoreStats;
    }
}
