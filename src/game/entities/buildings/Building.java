package game.entities.buildings;

import game.entities.Entity;

/**
 * Represents an entity the player can go to in order to buy items using coins or sell items to get coins in return.
 */
public class Building extends Entity {

    private int healthPotionsAvailable;
    private int damagePotionsAvailable;
    private final int maxPotionsAbleToBeStored;

    /**
     * Represents a Building object on the map.
     *
     * @param name                     The name of the building.
     * @param appearance               The appearance of the building.
     * @param healthPotionsAvailable   The number of health potions available in the building.
     * @param damagePotionsAvailable   The number of damage potions available in the building.
     * @param maxPotionsAbleToBeStored The maximum number of potions the building is able to store.
     * @param posX                     The x coordinate on the map.
     * @param posY                     The y coordinate on the map.
     */
    Building(String name, char appearance, int healthPotionsAvailable, int damagePotionsAvailable,
             int maxPotionsAbleToBeStored, int posX, int posY) {

        super(name, appearance, posX, posY);
        this.healthPotionsAvailable = healthPotionsAvailable;
        this.damagePotionsAvailable = damagePotionsAvailable;
        this.maxPotionsAbleToBeStored = maxPotionsAbleToBeStored;
    }

    /**
     * Gets the number of health potions available.
     *
     * @return The number of health potions available.
     */
    public int getHealthPotionsAvailable() {
        return this.healthPotionsAvailable;
    }

    /**
     * Gets the number of damage potions available.
     *
     * @return The number of damage potions available.
     */
    public int getDamagePotionsAvailable() {
        return this.damagePotionsAvailable;
    }

    /**
     * Changes the available number of health potions by a certain amount. If the number after the change is less than
     * 0 then the number of them is set to 0. Also, if the amount combined with the available health potions and damage
     * potions is greater than the maximum amount of potions available then the number of available health potions
     * aren't changed.
     *
     * @param amount The amount to change the number of available health potions.
     */
    public void changeHealthPotionsAvailable(int amount) {

        int healthPotionCombined = amount + this.healthPotionsAvailable;

        int combinedPotionCount = healthPotionCombined + this.damagePotionsAvailable;

        if (healthPotionCombined < 0) {
            this.healthPotionsAvailable = 0;

        } else if (combinedPotionCount <= this.maxPotionsAbleToBeStored) {
            this.healthPotionsAvailable += amount;
        }
    }

    /**
     * Changes the available number of damage potions by a certain amount. If the number after the change is less than
     * 0 then the number of them is set to 0. Also, if the amount combined with the available health potions and damage
     * potions is greater than the maximum amount of potions available then the number of available damage potions
     * aren't changed.
     *
     * @param amount The amount to change the number of available damage potions.
     */
    public void changeDamagePotionsAvailable(int amount) {

        int damagePotionCombined = amount + this.damagePotionsAvailable;

        int combinedPotionCount = damagePotionCombined + this.healthPotionsAvailable;

        if (damagePotionCombined < 0) {
            this.damagePotionsAvailable = 0;

        } else if (combinedPotionCount <= this.maxPotionsAbleToBeStored) {
            this.damagePotionsAvailable += amount;
        }
    }
}