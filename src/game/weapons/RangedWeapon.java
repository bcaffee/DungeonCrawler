package game.weapons;

public class RangedWeapon extends Weapon {

    public static final int MAX_RANGE = 5;
    public static final int MIN_RANGE = 2;

    /**
     * Represents a RangedWeapon object that is in the player's inventory.
     *
     * @param durability The number of uses before the weapon breaks.
     */
    RangedWeapon(int durability) {
        super(durability);
    }
}