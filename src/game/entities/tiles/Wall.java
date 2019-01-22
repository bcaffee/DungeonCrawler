package game.entities.tiles;

public class Wall extends Tile {

    private static final char WALL_SYMBOL = 'W';

    /**
     * Represents a Wall object on the map. The player can't move through walls.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public Wall(int posX, int posY) {
        super("Wall", WALL_SYMBOL, posX, posY);
    }
}
