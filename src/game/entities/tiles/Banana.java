package game.entities.tiles;

public class Banana extends Tile {

    private static final char BANANA_SYMBOL = '(';

    /**
     * Represents a Banana object on the map. The player takes less damage than the spikes, but gets moved 1 in the
     * x, -x, y or -y direction (down, up, right, left) if there is not a wall or other banana next to it.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public Banana(int posX, int posY) {
        super("Banana", BANANA_SYMBOL, posX, posY);
    }
}
