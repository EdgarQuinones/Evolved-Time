package com.edgarquinones.evolvedtime.evolvedtime;

public class Score {

    private final double personalInterest;
    private final double timeCommitment;
    private final double difficulty;
    private final double finalScore;

    public Score(double personalInterest, double timeCommitment, double difficulty) {
        this.personalInterest = personalInterest;
        this.timeCommitment = timeCommitment;
        this.difficulty = difficulty;
        finalScore = getScore();
    }

    private double getScore() {
        return (difficulty * (1.5 * timeCommitment)) / personalInterest;
    }

    @SuppressWarnings("unused")
    public double getPersonalInterest() {
        return personalInterest;
    }

    @SuppressWarnings("unused")
    public double getDifficulty() {
        return difficulty;
    }

    @SuppressWarnings("unused")
    public double getFinalScore() {
        return finalScore;
    }

}
