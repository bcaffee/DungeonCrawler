package game.entities.items;

public class HealthPotion extends Item {

    private static final char HEALTH_POTION_SYMBOL = '+';

    /**
     * Represents a Health Potion object on the map.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public HealthPotion(int posX, int posY) {
        super("Health Potion", HEALTH_POTION_SYMBOL, posX, posY);
    }
}
