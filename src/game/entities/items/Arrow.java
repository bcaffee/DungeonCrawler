package game.entities.items;

public class Arrow extends Item {

    private static final char ARROW_SYMBOL = '>';

    /**
     * Represents an Arrow object on the map.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    Arrow(int posX, int posY) {
        super("Arrow", ARROW_SYMBOL, posX, posY);
    }
}
