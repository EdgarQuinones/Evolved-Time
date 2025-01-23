package com.edgarquinones.evolvedtime.evolvedtime;

public class Score {

    private double personalInterest;
    private double timeCommitment;
    private double difficulty;
    private double finalScore;

    public Score(double personalInterest, double timeCommitment, double difficulty) {
        this.personalInterest = personalInterest;
        this.timeCommitment = timeCommitment;
        this.difficulty = difficulty;
        finalScore = getScore();
    }

    private double getScore() {
        return (difficulty * (1.5 * timeCommitment)) / personalInterest;
    }

    public double getPersonalInterest() {
        return personalInterest;
    }

    public void setPersonalInterest(double personalInterest) {
        this.personalInterest = personalInterest;
    }

    public double getTimeCommitment() {
        return timeCommitment;
    }

    public void setTimeCommitment(double timeCommitment) {
        this.timeCommitment = timeCommitment;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }
}
