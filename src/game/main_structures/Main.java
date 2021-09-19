package game.main_structures;

/**
 * Runs the whole program.
 */
public class Main {

    private static final MainMenu mainMenu = new MainMenu();

    public static void main(String[] args) {

        // Start the mainMenu
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

/*
 * TODO - Bugs:
 *
 * 1. { Is showing up when it isn't supposed to, probably connected to the null pointer created from map generation.
 *
 * TODO - Improvements and Ideas:
 *
 * 1. Change the coin system so the player only receives coins once they go to the shop and redeems them. Also, change
 * stats command to also show # of turns taken in the level and # of each enemy killed.
 *
 * 2. Only "boss" defined enemies (the dragon and the "upgraded/super modes" for the other enemies) can have the win
 * location on them. As the levels progress make the type and number of enemies spawning more difficult
 * (easier enemies for the first 1-3 levels, etc). Add zombie that can infect the player (lose health each turn even
 * after the fight. Add vampire that can drain (every so often in the fight when it deals damage to the player it gains
 * that much health). Wizard (immune to damage potion attacks and counters the player's healing potions, can use its
 * own damage + healing potions).
 *
 * 3. Odd Levels: Remove the win condition and instead the level will give the player a mission (like killing a specific
 * # of enemies within a range of turns or finding a special item. Have the player lose health and/or coins as a
 * consequence if they can't meet the mission.
 *
 * 4. Get the Sword (overall damage boost including when the enemy chooses to defend) and Bow (gives the player first
 * strike so they always take the first turn in a fight unless the enemy also has this status like a Skeleton or Dragon;
 * if this is the case then roll a die 50/50).
 *
 * 5. Even Levels: Create a magic compass item that the player can use a limited number of times to find the direction
 * of the win location. Make the win location at least 12 tiles away from the player's spawn.
 *
 * 6. Eventually add moving enemies.
 */