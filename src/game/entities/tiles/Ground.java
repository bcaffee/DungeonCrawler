package game.entities.tiles;

public class Ground extends Tile {

    private static final char GROUND_SYMBOL = '_';

    /**
     * Represents a Ground object on the map. It's empty space that the player can walk on/through.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public Ground(int posX, int posY) {
        super("Ground", GROUND_SYMBOL, posX, posY);
    }
}
