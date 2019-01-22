package game.entities.buildings;

public class Shop extends Building {

    private static final char SHOP_SYMBOL = '$';
    private static final int HEALTH_POTIONS_AVAILABLE = 4;
    private static final int DAMAGE_POTIONS_AVAILABLE = 4;
    private static final int MAX_AMOUNT_OF_POTIONS_AVAILABLE = 8;

    /**
     * Represents a Shop object on the map.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public Shop(int posX, int posY) {
        super("Shop", SHOP_SYMBOL, HEALTH_POTIONS_AVAILABLE, DAMAGE_POTIONS_AVAILABLE, MAX_AMOUNT_OF_POTIONS_AVAILABLE, posX, posY);
    }
}
