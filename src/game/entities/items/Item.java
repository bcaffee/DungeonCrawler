package game.entities.items;

import game.entities.Entity;

/**
 * Represents any entity a player can pick up.
 */
public class Item extends Entity {

    public static final int POTION_COST = 3;
    public static final int COINS_GIVEN_FOR_POTION = 2;

    public static final int MINIMUM_ITEM_COST = 3;

    /**
     * Represents an Item object on the map.
     *
     * @param name       The name of the item.
     * @param appearance The appearance of the item.
     * @param posX       The x coordinate on the map.
     * @param posY       The y coordinate on the map.
     */
    Item(String name, char appearance, int posX, int posY) {
        super(name, appearance, posX, posY);
    }
}