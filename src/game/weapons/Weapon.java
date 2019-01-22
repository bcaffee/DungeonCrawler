package game.weapons;

public class Weapon {

    private int durability;

    /**
     * Represents a Weapon object that is in the player's inventory.
     *
     * @param durability The number of uses before the weapon breaks.
     */
    Weapon(int durability) {
        this.durability = durability;
    }

    /**
     * Gets the weapon's durability.
     *
     * @return The weapon's durability.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Changes the weapon's durability by a certain amount. If the amount causes the durability to be less than 0 then
     * the durability is set to 0.
     *
     * @param amount The amount the durability is going to be changed by.
     */
    public void changeDurability(int amount) {
        this.durability += amount;

        if (this.durability < 0) {
            this.durability = 0;
        }
    }
}