package game.main_structures;

import game.entities.Player;
import game.entities.enemies.Enemy;
import game.weapons.Bow;

import java.util.Scanner;

/**
 * Handles all single commands and multi-commands like move, go, grab, look for the player on the map. Note:
 * There are separate commands in the Fight and TradeExchange class that are handled in the specific class, not the
 * EventHandler.
 */
abstract class EventHandler {

    private static final String[] COMMAND_LIST = {
            "move", "go", "grab", "look", "help", "stats", "quit"
    };

    private static String previousInput;

    /**
     * Updates each turn for the player on the map.
     *
     * @param map    The specific map that the player is on.
     * @param player The player that is on the map.
     */
    static void updateTurn(final Map map, Player player) {

        //Reads in the input words
        String[] inputStrings = readInput();

        String command = inputStrings[0];

        //User is asking for the help page
        if (command.equalsIgnoreCase("help")) {
            printHelp();

        } else if (command.equalsIgnoreCase("quit")) {
            System.out.println("Bye!");
            GameModule.turnGameOff();

        } else if (command.equalsIgnoreCase("stats")) {
            System.out.println("Health Potions: " + player.getHealthPotionCount());
            System.out.println("Damage Potions: " + player.getDamagePotionCount());
            System.out.println("Coins: " + player.getCoinCount());
            System.out.println();
            System.out.println("Your current health is: " + player.getCurrentHealth() + "/" + player.getMaxHealth());

        } else if (command.equalsIgnoreCase("shoot")) {

            if (player.getRangedWeapon() != null && player.getArrows() >= 1 && player.getRangedWeapon().getDurability() >= 1 && player.isInRangeOfClosestEnemy()) {

                Enemy closestEnemy = player.getClosestEnemy();
                int distance = player.getDistanceFromAnEnemy(closestEnemy);
                int damage = Bow.getDamage(distance);
                Enemy enemyToDealDamageTo = (Enemy) map.getEntityAt(closestEnemy.getPosX(), closestEnemy.getPosY());
                enemyToDealDamageTo.changeHealth(-damage);
                System.out.println("You dealt " + damage + " to the " + closestEnemy.getName() + ".");
                player.getRangedWeapon().changeDurability(-1);
                player.changeArrowCount(-1);
                System.out.println();

                //System.out.println("You have dealt damage to ");

            } else if (player.getRangedWeapon() != null && player.getRangedWeapon().getDurability() >= 1 && player.getArrows() >= 1) {

                System.out.println("Sorry you can't use your bow because you are either too close or to far away " +
                        "from the closest enemy.");
                System.out.println("The closest you can be is 2 tiles; the farthest is 5 tiles.");

            } else if (player.getRangedWeapon() != null && player.getRangedWeapon().getDurability() >= 1) {

                System.out.println("You are out of arrows.");
                System.out.println("You can go to the shop to buy more.");

            } else {
                System.out.println("You don't have a bow to use or your bow is broken because the durability ran out.");
            }

        } else if (command.equalsIgnoreCase("use mind vision")) {

            //TODO: Set up the interface for choosing the MIND VISION POTION

        } else if (command.equalsIgnoreCase("HackStats")) {
            player.changeHealth(15);
            player.changeDamagePotionCount(2);
            player.changeHealthPotionCount(2);
            player.changeCoinCount(4);
            System.out.println("You have successfully hacked your item and coin count.");
            System.out.println("You have successfully hacked your health.");

        } else if (validateInput(inputStrings)) {
            System.out.println("Not enough arguments given.");

            //Check if the first word is a valid command
        } else if (isValidCommand(command)) {

            switch (command) {

                case "move":
                case "go":
                    if (inputStrings[1].equals("up") ||
                            inputStrings[1].equals("down") ||
                            inputStrings[1].equals("left") ||
                            inputStrings[1].equals("right")) {
                        map.playerMove(convertStringToDirection(inputStrings[1]), player);
                    }
                    break;

                case "grab":
                    if (inputStrings[1].equals("up") ||
                            inputStrings[1].equals("down") ||
                            inputStrings[1].equals("left") ||
                            inputStrings[1].equals("right")) {
                        map.playerGrab(convertStringToDirection(inputStrings[1]), player);
                    }
                    break;

                case "look":
                    if (inputStrings[1].equals("up") ||
                            inputStrings[1].equals("down") ||
                            inputStrings[1].equals("left") ||
                            inputStrings[1].equals("right")) {
                        map.playerLook(convertStringToDirection(inputStrings[1]), player);
                    }
                    break;

                //This should never happen
                default:
                    System.out.println("Don't know how to handle " + command + ".");
                    break;
            }

        } else {
            System.out.println("Invalid Command Entered");
        }
    }

    /**
     * Prints the command list, instructions, and other general "how to play" tips.
     */
    static void printHelp() {
        System.out.println(" _    _        _");
        System.out.println("| |  | |      | |");
        System.out.println("| |__| |  ___ | | _ __");
        System.out.println("|  __  | / _ \\| || '_ \\       ");
        System.out.println("| |  | ||  __/| || |_) |_  _  _");
        System.out.println("|_|  |_| \\___||_|| .__/(_)(_)(_)");
        System.out.println("                 | |");
        System.out.println("                 |_|");
        System.out.println();

        //Print the commands with commas in between except on the last one
        System.out.print("List of acceptable commands: ");
        for (int i = 0; i < COMMAND_LIST.length; i++) {
            if (i == COMMAND_LIST.length - 1) {
                System.out.print(COMMAND_LIST[i]);
            } else {
                System.out.print(COMMAND_LIST[i] + ", ");
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("Try to survive as many dungeons (levels) as possible.");
        System.out.println("As you progress through each level, you can get coins by killing enemies. Use them sparingly.");
        System.out.println("Your score when you die is based on the level you get to.");
        System.out.println("The 'P' character is you (the player).");
        System.out.println("The move and go commands are the same. Example: move up or go down.");
        System.out.println("The look command followed by a direction (up, down, right, left) gives you information about" +
                " the space at that direction in relation to the player's position.");
        System.out.println("The grab command works the same way, but instead of getting information you can pick up items.");
        System.out.println("The stats command tells you how many turns you have taken in the current level, how much of each" +
                " item you have, number of enemy kill, and your health.");
        System.out.println("The quit command exits you out of the game.");
        System.out.println("Finally, the 'p' command (previous) executes the last command you typed.");
    }

    /**
     * Prints "What will you do" in bubble letters.
     */
    static void printWhatWillYouDo() {
        System.out.println(" _    _  _             _               _  _  _                             _        ___ ");
        System.out.println("| |  | || |           | |             (_)| || |                           | |      |__ \\");
        System.out.println("| |  | || |__    __ _ | |_  __      __ _ | || |  _   _   ___   _   _    __| |  ___    ) |");
        System.out.println("| |/\\| || '_ \\  / _` || __| \\ \\ /\\ / /| || || | | | | | / _ \\ | | | |  / _`| | / _ \\  / / ");
        System.out.println("\\  /\\  /| | | || (_| || |_   \\ V  V / | || || | | |_| || (_) || |_| | | (_| || (_) ||_|");
        System.out.println(" \\/  \\/ |_| |_| \\__,_| \\__|   \\_/\\_/  |_||_||_|  \\__, | \\___/  \\__,_|  \\__,_| \\___/ (_) ");
        System.out.println("                                                 __/ |");
        System.out.println("                                                |___/");
    }

    /**
     * Reads the player's input and separates it into different tokens.
     *
     * @return The players input split up into different parts of commands.
     */
    private static String[] readInput() {

        final Scanner input = new Scanner(System.in);

        //User input
        String inputString = input.nextLine();

        //If the user's input is P then their actual command will be their previous command
        if (inputString.equalsIgnoreCase("P")) {

            inputString = EventHandler.previousInput;

            //Otherwise the player didn't want to repeat their previous input, so update the previous input
        } else {
            EventHandler.previousInput = inputString;
        }

        //Display it to the player
        System.out.println("Input: " + inputString);

        //Split the line by spaces
        return inputString.split(" ");
    }

    /**
     * Checks to see if a command is valid.
     *
     * @param command The given command
     * @return Returns true or false based on if the command is valid or not.
     */
    private static boolean isValidCommand(String command) {

        //Iterate through the command list
        for (String aValidCommand : COMMAND_LIST) {

            //if the command is found, return true
            if (aValidCommand.equals(command)) {
                return true;
            }
        }
        //no matching command found in the list
        return false;
    }

    /**
     * Checks to see if the whole user input is valid or not. Not just a single command like isValidCommand().
     *
     * @param inputStrings The command that validity is being checked on.
     * @return Returns true or false based on if the user input is valid or not.
     */
    private static boolean validateInput(String[] inputStrings) {
        return inputStrings.length == 1 && (inputStrings[0].equalsIgnoreCase("go")
                || inputStrings[0].equalsIgnoreCase("move")
                || inputStrings[0].equalsIgnoreCase("grab")
                || inputStrings[0].equalsIgnoreCase("look")
                || inputStrings[0].equalsIgnoreCase("up")
                || inputStrings[0].equalsIgnoreCase("down")
                || inputStrings[0].equalsIgnoreCase("right")
                || inputStrings[0].equalsIgnoreCase("left"));
    }

    /**
     * Converts a string direction to an enum direction.
     *
     * @param direction The string direction being converted.
     * @return The enum direction corresponding to the string direction.
     */
    private static Direction convertStringToDirection(String direction) {

        Direction enumeratedDirection;

        switch (direction) {
            case "up":
                enumeratedDirection = Direction.UP;
                break;

            case "down":
                enumeratedDirection = Direction.DOWN;
                break;

            case "left":
                enumeratedDirection = Direction.LEFT;
                break;

            case "right":
                enumeratedDirection = Direction.RIGHT;
                break;

            //This should never happen
            default:
                enumeratedDirection = Direction.DEFAULT;
                break;
        }
        return enumeratedDirection;
    }
}