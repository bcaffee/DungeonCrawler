package game.entities.enemies;

import game.entities.Player;

public class BabyDragon extends Enemy {

    private static final char BABY_DRAGON_SYMBOL = 'd';

    static final int ATTACK_CHANCE = 71;
    static final int DEFEND_CHANCE = 16;
    static final int STALL_CHANCE = 13;

    static final int COINS_GIVEN = 4;

    private static final int HEALTH = 40;
    private static final int POWER = 4;
    private static final int ARMOR = 5;

    /**
     * Represents a BabyDragon object on the map.
     *
     * @param player The player object that is used to get the distance from this object to the player.
     * @param posX   The x coordinate on the map.
     * @param posY   The y coordinate on the map.
     */
    public BabyDragon(Player player, int posX, int posY) {
        super("Baby Dragon", BABY_DRAGON_SYMBOL, player, HEALTH, POWER, ARMOR, posX, posY);
    }
}