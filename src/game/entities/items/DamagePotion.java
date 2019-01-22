package game.entities.items;

public class DamagePotion extends Item {

    private static final char DAMAGE_POTION_SYMBOL = '-';

    /**
     * Represents a Damage Potion object on the map.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public DamagePotion(int posX, int posY) {
        super("Damage Potion", DAMAGE_POTION_SYMBOL, posX, posY);
    }
}