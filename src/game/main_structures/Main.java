package game.main_structures;

/**
 * Runs the whole program.
 */
abstract class Main {

    private static MainMenu mainMenu = new MainMenu();

    public static void main(String[] args) {

        //Start the mainMenu
        System.out.println("Welcome to Dungeon Crawler!");
        mainMenu.startScreen();
    }

    /**
     * Gets the main menu.
     *
     * @return The main menu.
     */
    static MainMenu getMainMenu() {
        return mainMenu;
    }
}

/*TODO:

1. Make the win location at least 7 tiles away from the player at the start of the game for odd levels, and for even
levels make it at least 11 tiles away.

2. Only the ground (blank tiles) and enemies can have the win location on them (for even levels).

3. Get the Sword working.

4. Get the Bow working.

5. Odd Levels: It will give you a goal
(kill a certain # of enemies or get a certain amount of coins in a certain amount of turns).

6. Even Levels: Use compass to find win location when the level starts and once after the player completes a goal
(kills a certain amount of enemies or gets a certain amount of coins in a specific # of turns.
*/