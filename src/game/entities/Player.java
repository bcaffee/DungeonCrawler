package game.entities;

import game.data.Level1Data;
import game.entities.enemies.Enemy;
//import game.entities.items.Item;
import game.weapons.MeleeWeapon;
import game.weapons.RangedWeapon;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a player object that moves around on the map.
 */
public class Player extends FightingEntity {

    private static final char APPEARANCE = 'P';
    private static final int MAX_HEALTH = 65;
    private static final int POWER = 5;
    private static final int ARMOR = 4;

    //private static final int MAX_INVENTORY_SIZE = 7;

    //private ArrayList<Item> inventory;

    private Enemy closestEnemy;

    private int healthPotionCount;
    private int damagePotionCount;
    private int coinCount;
    private int arrows;

    private MeleeWeapon meleeWeapon;
    private RangedWeapon rangedWeapon;

    /**
     * Represents a player object on the map.
     *
     * @param name The name of the player.
     * @param posX The x coordinate the player starts at.
     * @param posY The y coordinate the player starts at.
     */
    public Player(String name, int posX, int posY) {
        super(name, APPEARANCE, MAX_HEALTH, ARMOR, POWER, posX, posY);
        this.healthPotionCount = 0;
        this.damagePotionCount = 0;
        this.coinCount = Level1Data.STARTING_COINS;
        this.arrows = Level1Data.STARTING_ARROWS;
        //this.inventory = new ArrayList<>(MAX_INVENTORY_SIZE);
    }

    /**
     * Sets the closest enemy to the player on the map.
     *
     * @param map The map that is being used to find the closest enemy.
     */
    public void setClosestEnemy(Entity[][] map) {

        ArrayList<Enemy> enemiesInPlayerView = new ArrayList<>();

        //Find the enemies in the player's view grid
        for (int x = getPosX() - 2; x <= getPosX() + 2; x++) {
            for (int y = getPosY() - 2; y <= getPosY() + 2; y++) {

                if (x >= 0 && y >= 0 && x < map.length && y < map[0].length) {
                    if (map[x][y] instanceof Enemy) {
                        enemiesInPlayerView.add((Enemy) map[x][y]);
                    }
                }
            }
        }

        //Sort the distances
        Collections.sort(enemiesInPlayerView);

        if (!enemiesInPlayerView.isEmpty()) {
            this.closestEnemy = enemiesInPlayerView.get(0);
        }
    }

    /**
     * Gets the distance from the closest enemy.
     *
     * @return The distance from the closest enemy.
     */
    private int getDistanceFromClosestEnemy() {

        int distanceFromEnemy;
        distanceFromEnemy = (int) Math.sqrt(((getPosX() - this.closestEnemy.getPosX()) ^ 2) +
                ((getPosY() - this.closestEnemy.getPosY()) ^ 2));

        return distanceFromEnemy;
    }

    /**
     * Gets the distance from an enemy.
     *
     * @param enemy The enemy that is being used to calculate the distance from the player.
     * @return The distance from the enemy.
     */
    public int getDistanceFromAnEnemy(Enemy enemy) {

        int distanceFromEnemy;
        distanceFromEnemy = (int) Math.sqrt(((getPosX() - enemy.getPosX()) ^ 2) +
                ((getPosY() - enemy.getPosY()) ^ 2));

        return distanceFromEnemy;
    }

    /**
     * Gets the player's health potion count.
     *
     * @return The player's health potion count.
     */
    public int getHealthPotionCount() {
        return this.healthPotionCount;
    }

    /**
     * Gets the player's damage potion count.
     *
     * @return The player's damage potion count.
     */
    public int getDamagePotionCount() {
        return this.damagePotionCount;
    }

    /**
     * Gets the player's coin count.
     *
     * @return The player's coin count.
     */
    public int getCoinCount() {
        return this.coinCount;
    }

    /**
     * Gets the player's arrow count.
     *
     * @return The player's arrow count.
     */
    public int getArrows() {
        return this.arrows;
    }

    /**
     * Gets the closest enemy from the player.
     *
     * @return The closest enemy from the player.
     */
    public Enemy getClosestEnemy() {
        return this.closestEnemy;
    }

    /**
     * Gets the player's ranged weapon.
     *
     * @return The player's ranged weapon.
     */
    public RangedWeapon getRangedWeapon() {
        return this.rangedWeapon;
    }

    /**
     * Gets the player's melee weapon.
     *
     * @return The player's melee weapon.
     */
    public MeleeWeapon getMeleeWeapon() {
        return this.meleeWeapon;
    }

    /**
     * Gets if the player is in range of the closest enemy.
     *
     * @return If the player is in range of the closest enemy.
     */
    public boolean isInRangeOfClosestEnemy() {

        int distanceFromEnemy = getDistanceFromClosestEnemy();

        return distanceFromEnemy <= RangedWeapon.MAX_RANGE && distanceFromEnemy >= RangedWeapon.MIN_RANGE;
    }

    /**
     * Changes the player's health potion count by a certain amount.
     * If the count after the change is less than 0 then the count is changed to 0.
     *
     * @param numberOfHealthPotions The amount the count is changed by.
     */
    public void changeHealthPotionCount(int numberOfHealthPotions) {
        this.healthPotionCount += numberOfHealthPotions;

        if (this.healthPotionCount < 0) {
            this.healthPotionCount = 0;
        }
    }

    /**
     * Changes the player's damage potion count by a certain amount.
     * If the count after the change is less than 0 then the count is changed to 0.
     *
     * @param numberOfDamagePotions The amount the count is changed by.
     */
    public void changeDamagePotionCount(int numberOfDamagePotions) {
        this.damagePotionCount += numberOfDamagePotions;

        if (this.damagePotionCount < 0) {
            this.damagePotionCount = 0;
        }
    }

    /**
     * Changes the player's coin count by a certain amount.
     * If the count after the change is less than 0 then the count is changed to 0.
     *
     * @param numberOfCoins The amount the count is changed by.
     */
    public void changeCoinCount(int numberOfCoins) {
        this.coinCount += numberOfCoins;

        if (this.coinCount < 0) {
            this.coinCount = 0;
        }
    }

    /**
     * Changes the player's arrow count by a certain amount.
     * If the count after the change is less than 0 then the count is changed to 0.
     *
     * @param numberOfArrows The amount the count is changed by.
     */
    public void changeArrowCount(int numberOfArrows) {
        this.arrows += numberOfArrows;

        if (this.arrows < 0) {
            this.arrows = 0;
        }
    }

    /**
     * Sets the player's ranged weapon.
     *
     * @param rangedWeapon The new ranged weapon the player has.
     */
    public void setRangedWeapon(RangedWeapon rangedWeapon) {
        this.rangedWeapon = rangedWeapon;
    }

    /**
     * Sets the player's melee weapon.
     *
     * @param meleeWeapon The new melee weapon the player has.
     */
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }
}