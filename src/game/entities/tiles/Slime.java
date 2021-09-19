package game.entities.tiles;

public class Slime extends Tile {

    private static final char BANANA_SYMBOL = '(';

    /**
     * Represents a Slime object on the map. The player takes less damage than the spikes, but gets moved 1 in the
     * x, -x, y or -y direction (down, up, right, left) if there is not a wall or other slime next to it.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public Slime(int posX, int posY) {
        super("Slime", BANANA_SYMBOL, posX, posY);
    }
}
