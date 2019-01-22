package game.entities.items;

public class MindVisionPotion extends Item {

    private static final char MIND_VISION_POTION_SYMBOL = '*';

    /**
     * Represents a Mind Vision Potion object on the map.
     *
     * @param posX The x coordinate on the map.
     * @param posY The y coordinate on the map.
     */
    MindVisionPotion(int posX, int posY) {
        super("Mind Vision Potion", MIND_VISION_POTION_SYMBOL, posX, posY);
    }
}
