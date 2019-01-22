package game.main_structures;

import game.data.RandomData;
import game.entities.*;
import game.entities.buildings.Building;
import game.entities.buildings.Shop;
import game.entities.enemies.*;
import game.entities.items.DamagePotion;
import game.entities.items.HealthPotion;
import game.entities.items.Item;
import game.entities.items.WeaponPickUp;
import game.entities.tiles.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Represents an Entity matrix that has various methods to add, remove and get entities and collision locations. It also
 * figures out what type of collisions happen.
 */
class Map {

    private static final int ODD_MAP_LOWER_BOUND_WIDTH = 7;
    private static final int ODD_MAP_UPPER_BOUND_WIDTH = 15;
    private static final int ODD_MAP_LOWER_BOUND_HEIGHT = 3;
    private static final int ODD_MAP_UPPER_BOUND_HEIGHT = 7;

    private static final int EVEN_MAP_LOWER_BOUND_WIDTH = 17;
    private static final int EVEN_MAP_UPPER_BOUND_WIDTH = 30;
    private static final int EVEN_MAP_LOWER_BOUND_HEIGHT = 8;
    private static final int EVEN_MAP_UPPER_BOUND_HEIGHT = 15;

    static final int NUMBER_OF_COORDINATES = 2;

    private Entity[][] map;
    private int width;
    private int height;

    private boolean collision;
    private int[] collisionLoc = new int[NUMBER_OF_COORDINATES];
    private String collisionKind;

    private boolean looking;
    private Entity lookAt;

    /**
     * The Map constructor for the level 1 map.
     *
     * @param charMatrix The custom map made of a char matrix.
     * @param player     The player that will need to be placed on the map.
     */
    Map(char[][] charMatrix, Player player) {
        this.map = convertCharMatrixToEntityMatrix(charMatrix, player);
        this.width = this.map[0].length;
        this.height = this.map.length;
    }

    /**
     * The Map constructor for the non-level 1 maps. Even levels means the map is bigger and the player has to use a compass to
     * find the win location when the level starts and once after the player completes a goal
     * (kills a certain amount of enemies or gets a certain amount of coins in a specific # of turns.
     * Odd levels means the map is smaller and the player has one giant goal.
     *
     * @param level  The next level the player has to go through.
     * @param player The player that will need to be placed on the map.
     */
    Map(int level, Player player) {
        getRandomMap(level, player);
    }

    /**
     * Draws the map out to the player/console based on the player's coordinates.
     *
     * @param player The player that is used to draw the map. (The coordinates are used and the closest enemy to the
     *               player is set.
     */
    void draw(Player player) {

        //Actually draw the map (5 by 5 grid/matrix around the player)
        for (int x = player.getPosX() - 2; x <= player.getPosX() + 2; x++) {
            for (int y = player.getPosY() - 2; y <= player.getPosY() + 2; y++) {

                //Negative and positive map bounds for all corners/direction of the map
                if (x >= 0 && y >= 0 && x < this.height && y < this.width) {

                    //If we are displaying the player
                    if (player.getPosX() == x && player.getPosY() == y) {

                        System.out.print(player + " ");

                        //Otherwise we aren't
                    } else {
                        System.out.print(this.map[x][y] + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();

        //Reset the player's closest enemy
        player.setClosestEnemy(this.map);

        /*//Print the whole map
        for (int x = 0; x < this.height; x++)
            for (int y = 0; y < this.width; y++) {
                System.out.print(this.map[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();*/
    }

    /**
     * Clears a specific tile (turns it into a Ground entity).
     *
     * @param x The x coordinate that needs to be cleared.
     * @param y the y coordinate that needs to be cleared.
     */
    void removeEntityAt(int x, int y) {
        this.map[x][y] = new Ground(x, y);
    }

    /**
     * Controls how the player moves.
     *
     * @param direction The direction the player wants to move.
     * @param player    The player object that is going to move.
     */
    void playerMove(Direction direction, Player player) {

        int newX = player.getPosX();
        int newY = player.getPosY();

        switch (direction) {

            case UP:
                newX--;
                break;

            case DOWN:
                newX++;
                break;

            case RIGHT:
                newY++;
                break;

            case LEFT:
                newY--;
                break;

            default:
                System.out.println("playerMove(): This should never happen.");
                break;
        }

        Entity entity = this.map[newX][newY];

        //There will always be a collision
        this.collision = true;
        this.collisionLoc[0] = newX;
        this.collisionLoc[1] = newY;

        if (entity instanceof Enemy) {

            this.collisionKind = "enemy";

        } else if (entity instanceof Item) {

            this.collisionKind = "map";

        } else if (entity instanceof Tile) {

            this.collisionKind = "map";

        } else if (entity instanceof Building) {

            this.collisionKind = "building";

        } else {
            System.out.println("playerMove(): This should never happen.");
        }
    }

    /**
     * Controls how the player grabs things.
     *
     * @param direction The direction the player wants to grab.
     * @param player    The player object that is going to grab.
     */
    void playerGrab(Direction direction, Player player) {

        int newX = player.getPosX();
        int newY = player.getPosY();

        switch (direction) {

            case UP:
                newX--;
                break;

            case DOWN:
                newX++;
                break;

            case RIGHT:
                newY++;
                break;

            case LEFT:
                newY--;
                break;

            //This should never happen
            default:
                System.out.println("Direction is invalid.");
                break;
        }

        //There will always be a collision when you grab
        this.collision = true;
        this.collisionKind = "grab";
        this.collisionLoc[0] = newX;
        this.collisionLoc[1] = newY;
    }

    /**
     * Controls how the player looks at things.
     *
     * @param direction The direction the player wants to look.
     * @param player    The player object that is going to look.
     */
    void playerLook(Direction direction, Player player) {

        int newX = player.getPosX();
        int newY = player.getPosY();

        switch (direction) {

            case UP:
                newX--;
                break;

            case DOWN:
                newX++;
                break;

            case RIGHT:
                newY++;
                break;

            case LEFT:
                newY--;
                break;

            //This should never happen
            default:
                System.out.println("Direction is invalid.");
                break;
        }
        this.looking = true;
        this.lookAt = getEntityAt(newX, newY);
    }

    /**
     * Creates a random map.
     *
     * @param level  The level the player is on next.
     * @param player The player object that will be placed on the random map.
     */
    private void getRandomMap(int level, Player player) {

        int[] playerStartingLocationForRandomMap = new int[NUMBER_OF_COORDINATES];
        int[] winLocationForRandomMap = new int[NUMBER_OF_COORDINATES];
        int[] buildingLocationForRandomMap = new int[NUMBER_OF_COORDINATES];
        int[] weaponLocation = new int[NUMBER_OF_COORDINATES];

        Random random = new Random();

        //Even map
        int randomHeight;
        int randomWidth;

        if (level % 2 == 0) {

            //Get the randomHeight and randomWidth
            randomHeight = random.nextInt(EVEN_MAP_UPPER_BOUND_HEIGHT - EVEN_MAP_LOWER_BOUND_HEIGHT) +
                    EVEN_MAP_LOWER_BOUND_HEIGHT;
            randomWidth = random.nextInt(EVEN_MAP_UPPER_BOUND_WIDTH - EVEN_MAP_LOWER_BOUND_WIDTH) +
                    EVEN_MAP_LOWER_BOUND_WIDTH;

            playerStartingLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            playerStartingLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
            winLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            winLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
            buildingLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            buildingLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
            weaponLocation[0] = random.nextInt(randomHeight - 2) + 1;
            weaponLocation[1] = random.nextInt(randomWidth - 2) + 1;

            //Odd map
        } else {

            randomHeight = random.nextInt(ODD_MAP_UPPER_BOUND_HEIGHT - ODD_MAP_LOWER_BOUND_HEIGHT) +
                    ODD_MAP_LOWER_BOUND_HEIGHT;
            randomWidth = random.nextInt(ODD_MAP_UPPER_BOUND_WIDTH - ODD_MAP_LOWER_BOUND_WIDTH) +
                    ODD_MAP_LOWER_BOUND_WIDTH;

            playerStartingLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            playerStartingLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
            winLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            winLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
            buildingLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            buildingLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
            weaponLocation[0] = random.nextInt(randomHeight - 2) + 1;
            weaponLocation[1] = random.nextInt(randomWidth - 2) + 1;
        }

        //Make sure the shop's location isn't on the win location
        while (buildingLocationForRandomMap[0] == winLocationForRandomMap[0] &&
                buildingLocationForRandomMap[1] == winLocationForRandomMap[1]) {

            buildingLocationForRandomMap[0] = random.nextInt(randomHeight - 2) + 1;
            buildingLocationForRandomMap[1] = random.nextInt(randomWidth - 2) + 1;
        }

        RandomData.setPlayerStartLocation(playerStartingLocationForRandomMap);
        RandomData.setWinLocation(winLocationForRandomMap);
        RandomData.setBuildingLocation(buildingLocationForRandomMap);

        //Set up map
        this.height = randomHeight;
        this.width = randomWidth;
        this.map = new Entity[randomHeight][randomWidth];

        //Draw map
        drawPerimeter(this.map);
        drawInside(this.map, playerStartingLocationForRandomMap, buildingLocationForRandomMap, winLocationForRandomMap, weaponLocation, player);
    }

    /**
     * Fills the inside (non-perimeter) part of the entity matrix with random entities based on different weights.
     *
     * @param entityMatrix   The matrix being filled with walls around the perimeter.
     * @param playerStartPos The player's starting position on the map.
     * @param buildingLoc    The building location of the map.
     * @param winLoc         The win location of the map.
     * @param weaponLoc      The weapon location of the map
     * @param player         The player object that is placed on the map.
     */
    private void drawInside(Entity[][] entityMatrix, int[] playerStartPos, int[] buildingLoc, int[] winLoc, int[] weaponLoc, Player player) {

        //Place the one time entities
        placePlayerAt(player, playerStartPos[0], playerStartPos[1]);

        setEntityAt(new Ground(playerStartPos[0], playerStartPos[1]));

        //TODO: Randomly decide if the weapon is ranged or melee instead of just it being Bow

        setEntityAt(new WeaponPickUp('{', weaponLoc[0], weaponLoc[1]));

        //Make sure
        setEntityAt(new Shop(buildingLoc[0], buildingLoc[1]));

        for (int row = 1; row < entityMatrix.length - 1; row++) {

            for (int column = 1; column < entityMatrix[0].length - 1; column++) {

                //Get the category of the entity we are going to place
                String category = getCategory();

                Entity entity;

                //Depending on the category use the specific entity weights in the sub-category, then randomly get the entity
                switch (Objects.requireNonNull(category)) {

                    case "enemy":
                        entity = getEnemy(player, row, column);
                        break;

                    case "item":
                        entity = getItem(row, column);
                        break;

                    case "empty":
                        entity = new Ground(row, column);
                        break;

                    case "tile":
                        entity = getOtherTile(row, column);
                        break;

                    //This should never happen
                    default:
                        entity = null;
                        System.out.println("Invalid entity type!");
                        break;
                }

                /*If the entity being placed won't be at the same location as the shop and it's Ground where the
                player's coordinates are and also the entity isn't a wall being placed at the win location*/
                if (!((entity instanceof Ground && row == playerStartPos[0] && column == playerStartPos[1])
                        || (row == buildingLoc[0] && column == buildingLoc[1])
                        || (row == weaponLoc[0] && column == weaponLoc[1])
                        || !(entity instanceof Ground || entity instanceof Enemy)
                        && (row == winLoc[0] && column == winLoc[1]))) {

                    setEntityAt(Objects.requireNonNull(entity), row, column);
                }
            }
        }
    }

    /**
     * Converts a char matrix to an entity matrix.
     *
     * @param map    The char matrix being converted.
     * @param player The player object used to pass into getEntityFromAppearance().
     * @return The entity matrix that is the result of the conversion.
     */
    private Entity[][] convertCharMatrixToEntityMatrix(char[][] map, Player player) {

        Entity[][] temp = new Entity[map.length][map[0].length];

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                temp[row][col] = getEntityFromAppearance(map[row][col], player, row, col);
            }
        }
        return temp;
    }

    /**
     * Gets a specific entity given an appearance, a player object, and coordinates.
     *
     * @param appearance The appearance used to get the specific entity.
     * @param player     The player object used for enemies to get the distance between.
     * @param posX       The x coordinate.
     * @param posY       The y coordinate.
     * @return The specific entity.
     */
    private Entity getEntityFromAppearance(char appearance, Player player, int posX, int posY) {

        Entity entity;

        switch (appearance) {

            case 'g':
                entity = new Goblin(player, posX, posY);
                break;

            case 's':
                entity = new Skeleton(player, posX, posY);
                break;

            case 'D':
                entity = new Dragon(player, posX, posY);
                break;

            case 'd':
                entity = new BabyDragon(player, posX, posY);
                break;

            case '+':
                entity = new HealthPotion(posX, posY);
                break;

            case '-':
                entity = new DamagePotion(posX, posY);
                break;

            case 'W':
                entity = new Wall(posX, posY);
                break;

            case ')':
                entity = new Banana(posX, posY);
                break;

            case '^':
                entity = new Spike(posX, posY);
                break;

            case '_':
                entity = new Ground(posX, posY);
                break;

            case '$':
                entity = new Shop(posX, posY);
                break;

            case 'P':
                entity = player;
                break;

            default:
                System.out.println("getEntityFromAppearance(): This should never happen.");
                entity = null;
                break;
        }
        return entity;
    }

    /**
     * Fills the perimeter of the entity matrix with walls.
     *
     * @param entityMatrix The matrix being filled with walls around the perimeter.
     */
    private static void drawPerimeter(Entity[][] entityMatrix) {

        for (int row = 0; row < entityMatrix.length; row++) {
            for (int col = 0; col < entityMatrix[0].length; col++) {

                if (col == 0 || col == entityMatrix[0].length - 1 || row == entityMatrix.length - 1 || row == 0) {
                    entityMatrix[row][col] = new Wall(row, col);
                }
            }
        }
    }

    /**
     * Gets a specific category (enemy, tile, item, empty) based on different weights for the entity that will be
     * placed on the map.
     *
     * @return The category of the entity that will be placed on the map.
     */
    private static String getCategory() {

        java.util.Map<String, Double> generalWeights = new HashMap<>(4);

        //Put the percentages for the categories
        generalWeights.put("enemy", .1);
        generalWeights.put("item", .1);
        generalWeights.put("empty", .55);
        generalWeights.put("tile", .25);

        double ratio = new Random().nextDouble();
        double total = 0;

        for (String category : generalWeights.keySet()) {
            total += generalWeights.get(category);
            if (total >= ratio) {
                return category;
            }
        }
        return null;
    }

    /**
     * Gets a specific item based on different weights.
     *
     * @param posX The x position the item will be placed at.
     * @param posY The y position the item will be placed at.
     * @return The item that will be placed on the map.
     */
    private static Item getItem(int posX, int posY) {

        java.util.Map<Item, Double> itemWeights = new HashMap<>(2);
        itemWeights.put(new HealthPotion(posX, posY), .5);
        itemWeights.put(new DamagePotion(posX, posY), .5);

        double ratio = new Random().nextDouble();
        double total = 0;

        for (Item item : itemWeights.keySet()) {
            total += itemWeights.get(item);
            if (total >= ratio) {
                return item;
            }
        }
        return null;
    }

    /**
     * Gets a specific tile based on different weights.
     *
     * @param posX The x position the tile will be placed at.
     * @param posY The y position the tile will be placed at.
     * @return The tile that will be placed on the map.
     */
    private static Tile getOtherTile(int posX, int posY) {

        java.util.Map<Tile, Double> tileWeights = new HashMap<>(3);
        tileWeights.put(new Wall(posX, posY), .6);
        tileWeights.put(new Banana(posX, posY), .2);
        tileWeights.put(new Spike(posX, posY), .2);

        double ratio = new Random().nextDouble();
        double total = 0;

        for (Tile tile : tileWeights.keySet()) {
            total += tileWeights.get(tile);
            if (total >= ratio) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Gets a specific enemy based on different weights.
     *
     * @param player The player object used for calculating distance between it and an enemy.
     * @param posX   The x position the enemy will be placed at.
     * @param posY   The y position the enemy will be placed at.
     * @return The enemy that will be placed on the map.
     */
    private static Enemy getEnemy(Player player, int posX, int posY) {

        java.util.Map<Enemy, Double> enemyWeights = new HashMap<>(4);
        enemyWeights.put(new Goblin(player, posX, posY), .275);
        enemyWeights.put(new Skeleton(player, posX, posY), .325);
        enemyWeights.put(new Dragon(player, posX, posY), .15);
        enemyWeights.put(new BabyDragon(player, posX, posY), .25);

        double ratio = new Random().nextDouble();
        double total = 0;

        for (Enemy enemy : enemyWeights.keySet()) {
            total += enemyWeights.get(enemy);
            if (total >= ratio) {
                return enemy;
            }
        }
        return null;
    }

    /**
     * Sets a newly created entity on the map at the specific x and y position.
     *
     * @param e    The entity being placed.
     * @param posX The x position.
     * @param posY The y position.
     */
    private void setEntityAt(Entity e, int posX, int posY) {
        this.map[posX][posY] = e;
        e.setPosY(posY);
        e.setPosX(posX);
    }

    /**
     * Sets an entity on the map at its already set coordinates.
     *
     * @param e The entity being placed.
     */
    private void setEntityAt(Entity e) {
        this.map[e.getPosX()][e.getPosY()] = e;
    }

    /**
     * Gets the entity at a specific coordinate.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The entity at the specific coordinate.
     */
    Entity getEntityAt(int x, int y) {
        return this.map[x][y];
    }

    /**
     * Gets the location of the collision between the player and an entity.
     *
     * @return The location of the collision
     */
    int[] getCollisionLoc() {
        return this.collisionLoc;
    }

    /**
     * Gets if the player has collided.
     *
     * @return If the player has collided or not.
     */
    boolean isCollision() {
        return this.collision;
    }

    /**
     * Gets the type of collision that happened between the player and an entity.
     *
     * @return The type of collision.
     */
    String getCollisionKind() {
        return this.collisionKind;
    }

    /**
     * Gets if the player is looking.
     *
     * @return If the player is looking or not.
     */
    boolean isLooking() {
        return this.looking;
    }

    /**
     * Gets the entity the player is looking at.
     *
     * @return The entity the player is looking at.
     */
    Entity getLookAt() {
        return this.lookAt;
    }

    /**
     * Places the player at a specific coordinate.
     *
     * @param player The player object being placed.
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     */
    void placePlayerAt(Player player, int x, int y) {

        //Change it's coordinates
        player.setPosX(x);
        player.setPosY(y);
    }

    /**
     * Changes the player to not be looking anymore.
     */
    void notLookingAnymore() {
        this.looking = false;
    }

    /**
     * Changes the player to not be colliding anymore.
     */
    void notCollidingAnymore() {
        this.collision = false;
    }
}