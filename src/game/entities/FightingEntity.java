package game.entities;

/**
 * Represents an entity that can partake in a fight.
 */
public class FightingEntity extends Entity {

    //statistics of fighting entities
    private int currentHealth;
    private final int maxHealth;
    private final int power;
    private final int armor;
    private boolean isDefending;

    /**
     * Represents an entity that can partake in a fight.
     *
     * @param name       Name of the entity.
     * @param appearance Appearance of the entity.
     * @param health     The health of the entity.
     * @param power      The power level of the entity.
     * @param armor      The armor level of the entity.
     * @param posX       The x coordinate of the entity.
     * @param posY       The y coordinate of the entity.
     */
    public FightingEntity(String name, char appearance, int health, int power, int armor, int posX, int posY) {
        super(name, appearance, posX, posY);
        this.maxHealth = health;
        this.currentHealth = this.maxHealth;
        this.power = power;
        this.armor = armor;
    }

    /**
     * Gets if the entity is defending or not.
     *
     * @return If the entity is defending or not.
     */
    public boolean isDefending() {
        return this.isDefending;
    }

    /**
     * Gets the power level of the entity.
     *
     * @return The power level of the entity.
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the armor level of the entity.
     *
     * @return The armor level of the entity.
     */
    public int getArmor() {
        return this.armor;
    }

    /**
     * Gets the entity's current health.
     *
     * @return The entity's current health.
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Gets the entity's max health.
     *
     * @return The entity's max health.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Gets the string representation of the entity's current health out of its maximum.
     *
     * @return The string representation of the entity's current health out of its maximum.
     */
    public String healthString() {
        return (getName() + ": " + this.currentHealth + "/" + this.maxHealth + "HP");
    }

    /**
     * Sets if the entity is defending or not.
     *
     * @param defending If the entity is defending or not.
     */
    public void setDefending(boolean defending) {
        this.isDefending = defending;
    }

    /**
     * Changes the entity's health by a certain amount. If the health after the change is less than 0 then the health
     * is set to 0 and if the health becomes greater than the max then the heath is set to the max.
     *
     * @param change The amount the health is changed by.
     */
    public void changeHealth(int change) {

        //Change the health
        this.currentHealth += change;

        //Restrict health so if it's negative it goes to 0
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }

        //Restrict health so if it's above the max it will be set to the max
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }

    /* These could be used for special abilities when the power or armor are actual changed.
    At the moment (6/25/2018) there is no need for these.
    public void setPower(int power) {
        this.power = power;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }*/
}