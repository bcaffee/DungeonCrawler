package game.main_structures;

import game.data.*;
import game.entities.*;
import game.entities.buildings.Building;
import game.entities.buildings.Shop;
import game.entities.enemies.*;
import game.entities.items.*;
import game.entities.tiles.*;
import game.weapons.Bow;

import java.util.Random;
import java.util.Scanner;

/**
 * Keeps track of the turns of the player, all game collisions, and holds the level maps with the entities added.
 */
class GameModule {

    private static boolean playGame = true;

    /**
     * TODO 8/28/2018: Check what a turn accounts of.
     * A turn is counted as moving, grabbing, looking and your action in a fight.
     */
    private static int turn = 0;

    private Map map;
    private Player player;
    private int level;
    private int enemyInteractions = 0;

    /**
     * Starts the whole game.
     */
    void startGame() {
        loadLevel1();
        runGame();
    }

    /**
     * Goes through the main game loop.
     */
    private void runGame() {

        System.out.println();
        System.out.println("The map is below.");
        EventHandler.printWhatWillYouDo();

        while (GameModule.playGame) {

            //draw the map
            this.map.draw(this.player);

            //Update the turns
            GameModule.turn++;

            //ask the event handler to update the map
            EventHandler.updateTurn(this.map, this.player);

            //if there's a collision go handle it
            if (this.map.isCollision()) {
                handleCollision();
            }

            //if the player wants info
            if (this.map.isLooking()) {
                this.map.notLookingAnymore();
                Entity tile = this.map.getLookAt();
                info(tile);
            }

            //If the player has died
            if (this.player.getCurrentHealth() <= 0) {
                System.out.println("You have died! How Shameful... This game was made so you can't die.");
                System.out.println("You lasted " + GameModule.turn + " turns.");

                //Calculate the player's high score
                double highScore = this.level + this.player.getCoinCount();

                //Print it out
                System.out.println("Your score was " + highScore + ".");

                //I may need to change the comparison value later for the other levels
                if (this.enemyInteractions < 6) {
                    System.out.println("Your score may not be completely valid though because you didn't have enough enemy " +
                            "interactions.\nYour score won't be put on the leaderboard.");

                } else {

                    //Go back to Main Menu and log the score on the leaderboard.
                    Main.getMainMenu().getLeaderboard().logScoreOnLeaderboard(new Score(this.player.getName(), highScore));
                }
                turnGameOff();
            }

            //For checking if the player needs to go to level 2
            if (this.player.getPosX() == Level1Data.WIN_LOCATION[0] &&
                    this.player.getPosY() == Level1Data.WIN_LOCATION[1] && this.level == 1) {

                System.out.println("The map has changed since you are now on level 2.");
                this.player.changeCoinCount(2);
                System.out.println("You have been given 2 coins for your excellent job of completing level 1");
                loadLevel(2);

                //Otherwise it must be after level 2
            } else {

                int nextLevel = this.level + 1;
                int[] winLocation = RandomData.getWinLocation();

                if (winLocation != null) {

                    if (this.player.getPosX() == winLocation[0] &&
                            this.player.getPosY() == winLocation[1] && this.level > 1) {
                        System.out.println("The map has changed since you are now on level " + nextLevel + ".");
                        this.player.changeCoinCount(nextLevel);
                        System.out.println("You have been given " + nextLevel + " coins for your excellent job of completing level " + this.level + ".");
                        loadLevel(nextLevel);
                    }
                }
            }
        }
    }

    /**
     * Handles all game collisions.
     */
    private void handleCollision() {

        //Since we are handling the collision reset it to false
        this.map.notCollidingAnymore();

        //Get where the collision happened
        int[] collidedAt = this.map.getCollisionLoc();

        //Get the tile we collided with
        Entity entity = this.map.getEntityAt(collidedAt[0], collidedAt[1]);

        switch (this.map.getCollisionKind()) {

            case "grab": {

                Entity collider = this.map.getEntityAt(collidedAt[0], collidedAt[1]);

                if (collider instanceof Enemy) {
                    System.out.println("The " + collider.getName() + " can't be grabbed.\nAre you crazy!?");

                } else if (collider instanceof Item) {

                    switch (collider.getName()) {

                        case "Health Potion":
                            System.out.println("You have grabbed a Health Potion. You can use it in battle when it's your turn, by choosing the [item] option.\n" +
                                    "It increases your health by 10.");
                            this.map.removeEntityAt(collidedAt[0], collidedAt[1]);
                            this.player.changeHealthPotionCount(1);
                            break;

                        case "Damage Potion":
                            System.out.println("You have grabbed a Damage Potion. You can use it in battle when it's your turn, by choosing the [item] option.\n" +
                                    "It decreases your enemy's health by 10.");
                            this.map.removeEntityAt(collidedAt[0], collidedAt[1]);
                            this.player.changeDamagePotionCount(1);
                            break;

                        case "Weapon Pickup":
                            System.out.println("You have grabbed a Bow. You can use it if you are in range of an enemy by typing \"use bow\".");
                            System.out.println("You have to be within a 2-5 tile distance from an enemy (inclusive)." +
                                    "\nThe closer you are the more damage you do; the farther away you are the less damage you do.");
                            System.out.println("Every turn you use the bow and deal damage to an enemy you will have one less " +
                                    "turn to use it in the future.\nStarting at 5 turns.");
                            this.map.removeEntityAt(collidedAt[0], collidedAt[1]);
                            this.player.setRangedWeapon(new Bow());
                            break;

                        default:
                            System.out.println("handleCollision(): This should never happen.");
                            break;
                    }

                } else if (collider instanceof Tile) {

                    if (entity instanceof Ground) {
                        System.out.println("Grab what, the ground?");

                    } else if (entity instanceof Wall) {
                        System.out.println("Do you think your hands are big enough to grab walls?");

                    } else if (entity instanceof Slime) {
                        System.out.println("Sorry, you can't grab slime!");

                    } else if (entity instanceof Spike) {
                        System.out.println("Why in the world would you want to grab a spike?!");

                    } else {
                        System.out.println("handleCollision(): This should never happen.");
                    }

                    //Otherwise the collider must be of type Building
                } else {
                    System.out.println("Just like walls the " + collider.getName() + " can't be grabbed.");
                }
                break;
            }

            case "enemy": {

                this.enemyInteractions++;

                //get the enemy that the player collided with
                Enemy collider = (Enemy) this.map.getEntityAt(collidedAt[0], collidedAt[1]);

                //Start the fight
                Fight mapFight = new Fight(this.player, collider);

                //If the enemy is a dragon or skeleton then the enemy has the advantage
                if (collider.getName().equals("Dragon") || collider.getName().equals("Skeleton")) {
                    mapFight.startFight("enemy");

                } else {
                    mapFight.startFight("player");
                }

                //Update the new player after the fight
                this.player = mapFight.getModifiedPlayer();

                //if the player won
                if (mapFight.getVictor().equals("player")) {

                    //remove what was defeated
                    this.map.removeEntityAt(collidedAt[0], collidedAt[1]);

                    //put the player where the enemy was
                    this.map.placePlayerAt(this.player, collidedAt[0], collidedAt[1]);
                }
            }
            break;

            case "building":

                //get the building that the player collided with
                Building collider = (Building) this.map.getEntityAt(collidedAt[0], collidedAt[1]);

                boolean playerCanSell = this.player.getDamagePotionCount() > 0 || this.player.getHealthPotionCount() > 0;
                boolean playerCanBuy = this.player.getCoinCount() >= Item.MINIMUM_ITEM_COST;
                boolean shopCanSell = collider.getHealthPotionsAvailable() > 0 || collider.getDamagePotionsAvailable() > 0;

                System.out.println();

                //If the player has items to sell and the shop can buy, or coins to buy then give them the acceptable options
                if ((shopCanSell && playerCanBuy) || playerCanSell) {

                    System.out.println("Welcome to the shop. You can buy and sell potions here.");
                    System.out.println("Damage and health potions both cost 3 coins each. Also you will get 2 coins per one you sell.");
                    System.out.println("You will automatically be kicked out if you don't have any items to sell or enough coins for buying.");
                    System.out.println("Once you come in you can leave whenever.");

                    //Start the exchange
                    TradeExchange tradeExchange = new TradeExchange(this.player, collider);
                    new TradeExchange(this.player, collider).startExchange();
                    tradeExchange.startExchange();

                    //Update the new player after the exchange is done
                    this.player = tradeExchange.getModifiedPlayer();

                    //If the shop can't sell then say the shop is sold out
                } else if (!shopCanSell) {

                    System.out.println("Sorry the shop is sold out and you don't have any items to sell.");

                    //Otherwise the player doesn't, so tell them they need more coins, and how to get them, and also kick them ou
                } else {
                    System.out.println("Sorry looks like you don't have any coins to buy an item and you don't have any items to sell.");
                    System.out.println("Damage and health potions both cost 3 coins.");
                    System.out.println("If you want some more coins then fight enemies. Dragons give you 6, baby dragons 4, skeletons 3, and goblins 2.");
                }
                break;

            //game.main_structures.Map collider
            default: {

                if (entity instanceof Wall) {
                    System.out.println(this.player.getName() + " can't walk through walls!");

                } else if (entity instanceof Slime) {
                    System.out.println(this.player.getName() + " steps on some slime. You slip, fall, and lose 1 health.");
                    this.player.changeHealth(-1);

                    //Don't keep the slime
                    this.map.removeEntityAt(entity.getPosX(), entity.getPosY());

                    //Slide the player an additional tile in the y direction
                    this.map.placePlayerAt(this.player, collidedAt[0], collidedAt[1] + 1);

                } else if (entity instanceof Spike) {
                    System.out.println(this.player.getName() + " steps on a spike and it hurts.");
                    System.out.println("You take 2 damage.");

                    this.player.changeHealth(-2);

                    this.map.placePlayerAt(this.player, collidedAt[0], collidedAt[1]);

                } else if (entity instanceof Ground) {

                    //Put the player in the new spot
                    this.map.placePlayerAt(this.player, collidedAt[0], collidedAt[1]);

                    //This should happen if the player tries to walk through/over an item
                } else if (entity instanceof HealthPotion || entity instanceof DamagePotion) {
                    System.out.println("You can't walk through items. If you want to pick it up then use \"grab\" and then its direction.");

                } else {
                    System.out.println("handleCollision: This should never happen.");
                }
                break;
            }
        }
    }

    /**
     * Gives the player info based on a specific entity.
     *
     * @param entity The entity that the player is trying to get info for.
     */
    private void info(Entity entity) {

        if (entity instanceof Ground) {
            System.out.println("Look at what?");

        } else if (entity instanceof Wall) {
            System.out.println("That's a wall.");

        } else if (entity instanceof Slime) {
            System.out.println("That's some slime.");

        } else if (entity instanceof Spike) {
            System.out.println("That looks like it might hurt.");

            //enemies
        } else if (entity instanceof Goblin) {
            System.out.println("That's a goblin!");

        } else if (entity instanceof Skeleton) {
            System.out.println("That's a skeleton... You might want to be aware of it's super fancy bow (It goes first in combat).");

        } else if (entity instanceof Dragon) {
            System.out.println("That's a dragon!!! You might as well be doomed.\nWarning: When it fights it goes first.");

        } else if (entity instanceof BabyDragon) {
            System.out.println("That's a baby... dragon. Almost forgot to say \"dragon\" at the end.");

            //items
        } else if (entity instanceof HealthPotion) {
            System.out.println("That's a health potion. You probably want to pick it up.");

        } else if (entity instanceof DamagePotion) {
            System.out.println("That's a damage potion. You probably want to pick it up.");

            //buildings
        } else if (entity instanceof Shop) {
            System.out.println();
            System.out.println("That's a shop. You may want to go in and buy something with your coins.");
            System.out.println("Damage and health potions both cost 3 coins each. Also you will get 2 coins per one you sell.");
            System.out.println("You will automatically be kicked out if you don't have any items to sell or enough coins to buy.");
            System.out.println("Once you come in you can leave whenever.");

            //This should never happen
        } else {
            System.out.println("That's something that doesn't exist.");
        }
    }

    /**
     * Changes the map based on what level the player needs to go to next.
     *
     * @param level The level the player needs to go to next.
     */
    private void loadLevel(int level) {
        this.level = level;
        this.map = new Map(level, this.player);
    }

    /**
     * Asks for a given player name and loads the first level.
     */
    private void loadLevel1() {

        this.level = 2;

        System.out.println();
        System.out.println("What do you want your name to be?");

        //Get the name of the player
        final Scanner getName = new Scanner(System.in);
        String chosenName = getName.nextLine();

        ///*
        //TODO: The below is TEMPORARY RANDOM STUFF FOR LEVEL 2 TESTING SO THERE IS NO NULL POINTERS
        int[] playerStartingLocationForRandomMap = new int[Map.NUMBER_OF_COORDINATES];

        Random random = new Random();

        final int EVEN_MAP_LOWER_BOUND_WIDTH = 17;
        final int EVEN_MAP_UPPER_BOUND_WIDTH = 30;
        final int EVEN_MAP_LOWER_BOUND_HEIGHT = 8;
        final int EVEN_MAP_UPPER_BOUND_HEIGHT = 15;

        int randomHeight = random.nextInt(EVEN_MAP_UPPER_BOUND_HEIGHT - EVEN_MAP_LOWER_BOUND_HEIGHT) +
                EVEN_MAP_LOWER_BOUND_HEIGHT;
        int randomWidth = random.nextInt(EVEN_MAP_UPPER_BOUND_WIDTH - EVEN_MAP_LOWER_BOUND_WIDTH) +
                EVEN_MAP_LOWER_BOUND_WIDTH;

        playerStartingLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
        playerStartingLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;

        RandomData.setPlayerStartLocation(playerStartingLocationForRandomMap);
        //*/

        //For random map testing
        int[] playerStart = RandomData.getPlayerStartLocation();

        //Create the player
        this.player = new Player(chosenName, playerStart[0], playerStart[1]);

        //this.map = new Map(Level1Data.MAP, this.player);

        this.map = new Map(this.level, this.player);

        /*
        Put the player on the map

         this.map.placePlayerAt(this.player, Level1Data.PLAYER_STARTING_LOCATION[0],
               Level1Data.PLAYER_STARTING_LOCATION[1]);
        */
    }

    /**
     * Turns the game off which triggers the main menu to come up.
     */
    static void turnGameOff() {
        GameModule.playGame = false;
    }

    /**
     * Increments the player's turn.
     */
    static void incrementTurns() {
        GameModule.turn++;
    }
}