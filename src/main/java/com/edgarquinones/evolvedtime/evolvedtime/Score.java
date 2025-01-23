package com.edgarquinones.evolvedtime.evolvedtime;

/**
 * Used to keep track of the specific values of each task,
 * and the class Task will store this object.
 */
public class Score {

    private final double personalInterest;
    private final double timeCommitment;
    private final double difficulty;

    /**
     * Constructor
     * @param personalInterest How hard is the task
     * @param timeCommitment How long will this task take
     * @param difficulty How much does the user enjoy doing this activity
     */
    public Score(double personalInterest, double timeCommitment, double difficulty) {
        this.personalInterest = personalInterest;
        this.timeCommitment = timeCommitment;
        this.difficulty = difficulty;
    }

    /**
     * Get the calculated score from the algorithm,
     * will be used more once task filter is added
     * @return Calculated score
     */
    @SuppressWarnings("unused")
    private double getScore() {
        return (difficulty * (1.5 * timeCommitment)) / personalInterest;
    }

    /**
     * Returns the personal interest of the score.
     * Currently UNUSED as I plan to use it when a
     * task filter is added.
     * @return The personal interest
     */
    @SuppressWarnings("unused")
    public double getPersonalInterest() {
        return personalInterest;
    }

    /**
     * Returns the difficulty of the score.
     * Currently UNUSED as I plan to use it when a
     * task filter is added.
     * @return The difficulty
     */
    @SuppressWarnings("unused")
    public double getDifficulty() {
        return difficulty;
    }

}
