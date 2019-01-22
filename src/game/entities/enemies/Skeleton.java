package game.entities.enemies;

import game.entities.Player;

public class Skeleton extends Enemy {

    private static final char SKELETON_SYMBOL = 's';

    static final int ATTACK_CHANCE = 69;
    static final int DEFEND_CHANCE = 20;
    static final int STALL_CHANCE = 11;

    static final int COINS_GIVEN = 3;

    private static final int HEALTH = 30;
    private static final int POWER = 4;
    private static final int ARMOR = 4;

    /**
     * Represents a Skeleton object on the map.
     *
     * @param player The player object that is used to get the distance from this object to the player.
     * @param posX   The x coordinate on the map.
     * @param posY   The y coordinate on the map.
     */
    public Skeleton(Player player, int posX, int posY) {
        super("Skeleton", SKELETON_SYMBOL, player, HEALTH, POWER, ARMOR, posX, posY);
    }
}