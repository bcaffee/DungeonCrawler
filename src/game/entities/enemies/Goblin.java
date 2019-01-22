package game.entities.enemies;

import game.entities.Player;

public class Goblin extends Enemy {

    private static final char GOBLIN_SYMBOL = 'g';

    static final int ATTACK_CHANCE = 65;
    static final int DEFEND_CHANCE = 23;
    static final int STALL_CHANCE = 12;

    static final int COINS_GIVEN = 2;

    private static final int HEALTH = 25;
    private static final int POWER = 3;
    private static final int ARMOR = 4;

    /**
     * Represents a Goblin object on the map.
     *
     * @param player The player object that is used to get the distance from this object to the player.
     * @param posX   The x coordinate on the map.
     * @param posY   The y coordinate on the map.
     */
    public Goblin(Player player, int posX, int posY) {
        super("Goblin", GOBLIN_SYMBOL, player, HEALTH, POWER, ARMOR, posX, posY);
    }
}