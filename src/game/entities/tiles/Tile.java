package game.entities.tiles;

import game.entities.Entity;

public class Tile extends Entity {

    /**
     * Represents a Tile object on the map. Tiles are considered walls, the ground/empty space, spikes, and bananas.
     *
     * @param name       The name of the tile.
     * @param appearance The appearance of the tile.
     * @param posX       The x coordinate on the map.
     * @param posY       The y coordinate on the map.
     */
    Tile(String name, char appearance, int posX, int posY) {
        super(name, appearance, posX, posY);
    }
}