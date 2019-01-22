package game.entities.tiles;

public class Spike extends Tile {

    private static final char SPIKE_SYMBOL = '^';

    /**
     * Represents a Spike object on the map. Spikes deal damage to the player and don't disappear after they are stepped on.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    public Spike(int posX, int posY) {
        super("Spike", SPIKE_SYMBOL, posX, posY);
    }
}
