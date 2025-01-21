package com.edgarquinones.evolvedtime.evolvedtime;

public class Score {
    private double difficulty;
    private double timeCommitment;
    private double personalInterest;

    public Score(double difficulty, double timeCommitment, double personalInterest) {
        this.difficulty = difficulty;
        this.timeCommitment = timeCommitment;
        this.personalInterest = personalInterest;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getTimeCommitment() {
        return timeCommitment;
    }

    public void setTimeCommitment(double timeCommitment) {
        this.timeCommitment = timeCommitment;
    }

    public double getPersonalInterest() {
        return personalInterest;
    }

    public void setPersonalInterest(double personalInterest) {
        this.personalInterest = personalInterest;
    }
}
