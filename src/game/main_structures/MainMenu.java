package game.main_structures;

import java.util.*;

/**
 * Has the GUI for the main menu where the player can play the game, see the commands/rules/instructions of the game,
 * and the high scores.
 */
class MainMenu {

    private final Leaderboard leaderboard;

    /**
     * The main menu constructor.
     */
    MainMenu() {
        this.leaderboard = new Leaderboard();
    }

    /**
     * Displays to the player the start screen options ("Play Game", "How to Play", "High Scores", and "Leave").
     */
    void startScreen() {
        System.out.println();
        System.out.println("Please enter a choice from the below options.");
        System.out.println("(Options: [Play Game] [How To Play] [High Scores] [Leave])");
        playerChoice();
    }

    /**
     * Handles the player's choice.
     */
    private void playerChoice() {

        final Scanner input = new Scanner(System.in);
        String choice = input.nextLine();

        boolean done = false;

        while (!done) {

            if (choice.equalsIgnoreCase("Play Game")) {

                GameModule gameModule = new GameModule();
                gameModule.startGame();

            } else if (choice.equalsIgnoreCase("How to Play")) {

                EventHandler.printHelp();

            } else if (choice.equalsIgnoreCase("High Scores")) {

                System.out.println();

                ArrayList<Score> highScores = this.leaderboard.getScores();

                //If highScores isn't empty then print out the scores
                if (!highScores.isEmpty()) {

                    for (Score score : highScores) {
                        System.out.println(score);
                    }

                    //Otherwise the game hasn't been played yet, so tell the user
                } else {
                    System.out.println("There are no high scores available because the game hasn't been played yet.");
                }

            } else if (choice.equalsIgnoreCase("Leave")) {

                done = true;
                System.out.println("Bye!");

            } else {
                System.out.println("Sorry that wasn't an option given.\nPlease enter your choice again.");
            }

            if (!done) {
                startScreen();
            } else {
                input.close();
            }
        }
    }

    /**
     * Gets the leaderboard the main menu stores.
     *
     * @return The leaderboard.
     */
    Leaderboard getLeaderboard() {
        return this.leaderboard;
    }
}