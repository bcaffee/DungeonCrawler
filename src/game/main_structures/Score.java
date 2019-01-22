package game.main_structures;

class Score implements Comparable<Score> {

    private String playerName;
    private double score;

    /**
     * The Score constructor which represents a player's name and their score.
     *
     * @param playerName The player's name.
     * @param score      The score of the player.
     */
    Score(String playerName, double score) {
        this.playerName = playerName;
        this.score = score;
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name.
     */
    String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the player's score.
     *
     * @return The player's score.
     */
    double getScore() {
        return this.score;
    }

    /**
     * Gets the string representation of the score object.
     *
     * @return The string representation of the score object.
     */
    @Override
    public String toString() {
        return this.playerName + ": " + this.score;
    }

    /**
     * Gets an integer value that is the result of a comparison between this score object and another score object.
     *
     * @param score The other score object being compared.
     * @return An integer value that is the result of the comparison.
     */
    @Override
    public int compareTo(Score score) {
        //Using the Double class' overrided compareTo
        return Double.compare(this.score, score.getScore());
    }
}
