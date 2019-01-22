package game.weapons;

/**
 * Represents a melee weapon a player can pick up and to use in combat.
 */
public class MeleeWeapon extends Weapon {

    private int extraPower;

    /**
     * Represents a MeleeWeapon object that is in the player's inventory.
     *
     * @param durability The number of uses before the weapon breaks.
     * @param extraPower The extra power that the melee weapon would give to the player during combat.
     */
    MeleeWeapon(int durability, int extraPower) {
        super(durability);
        this.extraPower = extraPower;
    }

    /*
    public int getExtraPower() {
        return this.extraPower;
    }

    /*
    public void setExtraPower(int extraPower) {
        this.extraPower = extraPower;
    }
    */
}