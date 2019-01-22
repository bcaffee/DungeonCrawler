package game.entities.enemies;

import game.entities.Player;

public class Dragon extends Enemy {

    private static final char DRAGON_SYMBOL = 'D';

    static final int ATTACK_CHANCE = 75;
    static final int DEFEND_CHANCE = 15;
    static final int STALL_CHANCE = 10;

    static final int COINS_GIVEN = 6;

    private static final int HEALTH = 50;
    private static final int POWER = 5;
    private static final int ARMOR = 5;

    /**
     * Represents a Dragon object on the map.
     *
     * @param player The player object that is used to get the distance from this object to the player.
     * @param posX   The x coordinate on the map.
     * @param posY   The y coordinate on the map.
     */
    public Dragon(Player player, int posX, int posY) {
        super("Dragon", DRAGON_SYMBOL, player, HEALTH, POWER, ARMOR, posX, posY);
    }
}