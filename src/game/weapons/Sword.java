package game.weapons;

public class Sword extends MeleeWeapon {

    public static final char SWORD_SYMBOL = '!';

    public static final int SWORD_COST = 5;
    public static final int COINS_GIVEN_FOR_SWORD = 3;

    private static final int SWORD_STRENGTH = 3;
    private static final int SWORD_DURABILITY = 5;

    /**
     * Represents a Sword object in the player's inventory. Used to deal a specific amount of damage more to enemies.
     */
    public Sword() {
        super(SWORD_DURABILITY, SWORD_STRENGTH);
    }
}