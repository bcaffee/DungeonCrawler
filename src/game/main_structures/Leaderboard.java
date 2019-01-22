package game.main_structures;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Leaderboard {

    private static ArrayList<Score> scores;

    /**
     * The Leaderboard constructor that represents all scores in HighScores.txt.
     */
    Leaderboard() {
        Leaderboard.scores = new ArrayList<>();
    }

    /**
     * Logs a score on the leaderboard.
     *
     * @param score The score being logged.
     */
    void logScoreOnLeaderboard(Score score) {

        try {
            writeFile(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Leaderboard.scores.add(score);
    }

    /**
     * Writes a score to HighScore.txt.
     *
     * @param score The score that will be written to HighScore.txt.
     * @throws IOException If the file HighScore.txt isn't found.
     */
    private void writeFile(Score score) throws IOException {

        String scoreLine = score.getPlayerName() + ": " + score.getScore() + "\n";

        Files.write(Paths.get("HighScore.txt"), scoreLine.getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Reads HighScore.txt.
     *
     * @throws IOException If the file HighScore.txt isn't found.
     */
    private void readFile() throws IOException {

        //Create a new stream that gets the lines in HighScore.txt
        Stream<String> scores = Files.lines(Paths.get("HighScore.txt"));

        /*For each line in the scores we are going to map each line, into a word array by splitting them,
        get the score and then add that score to a temp ArrayList that is created by using .collect to turn the
        Stream<String> into a collection of type ArrayList and set highScores to it.*/
        Leaderboard.scores = scores.map(line -> {
            String[] words = line.split(": ");
            return new Score(words[0], Double.parseDouble(words[1]));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sorts the scores in descending order.
     */
    private static void sortScores() {
        Leaderboard.scores.sort(Collections.reverseOrder());
    }

    /**
     * Gets the scores, so they can be displayed to the user.
     *
     * @return The scores that will be displayed to the user.
     */
    ArrayList<Score> getScores() {

        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sortScores();

        return Leaderboard.scores;
    }
}