package game.weapons;

public class Bow extends RangedWeapon {

    public static final char BOW_SYMBOL = '{';

    public static final int BOW_COST = 6;
    public static final int COINS_GIVEN_FOR_BOW = 3;

    private static final int BOW_DURABILITY = 4;

    /**
     * Represents a Bow object in the player's inventory.
     */
    public Bow() {
        super(BOW_DURABILITY);
    }

    /**
     * Gets the amount of damage that will be dealt to the closest enemy given the player's distance from the enemy.
     * Distance is found using the pythagorean theorem. If the player's distance from the enemy is 2 then 4-6 damage
     * can be dealt. Distance 3 is 3-5, distance 4 is 2-4, distance 5 is 0-2. The distance has to be between 2 and 5
     * tiles inclusively to be able to do damage to the enemy.
     *
     * @param distanceFromEnemy The player's distance from the enemy.
     * @return The amount of damage that will be dealt to the closest enemy.
     */
    public static int getDamage(int distanceFromEnemy) {

        int damage = 0;

        if (distanceFromEnemy == 2) {

            //[4, 6]
            damage = 4 + (int) (Math.random() * 3);

        } else if (distanceFromEnemy == 3) {

            //[3, 5]
            damage = 3 + (int) (Math.random() * 3);

        } else if (distanceFromEnemy == 4) {

            //[2, 4]
            damage = 2 + (int) (Math.random() * 3);

        } else if (distanceFromEnemy == 5) {

            //[0, 2]
            damage = (int) (Math.random() * 3);

        } else {
            System.out.println("getDamage(): This should never happen.");
        }
        return damage;
    }
}