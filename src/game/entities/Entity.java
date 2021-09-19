package game.entities;

public class Entity {

    private final String name;
    private final char appearance;
    private int posX;
    private int posY;

    /**
     * Represents a map object or in other words, anything that can be placed on a map.
     * There are 4 major groupings of map objects as of 8/29/2018.
     * They are enemies, items, buildings, and tiles. The player is a map object, but not in a specific grouping.
     *
     * @param name       The name of the map object.
     * @param appearance The appearance of the map object.
     * @param posX       The x coordinate of the map object.
     * @param posY       The y coordinate of the map object.
     */
    public Entity(String name, char appearance, int posX, int posY) {
        this.name = name;
        this.appearance = appearance;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets the entity's name.
     *
     * @return The entity's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the string representation of an entity (which is just its appearance).
     *
     * @return The entity's appearance.
     */
    @Override
    public String toString() {
        return "" + this.appearance;
    }

    /**
     * Gets the x coordinate of the entity.
     *
     * @return The x coordinate of the entity.
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     * Gets the y coordinate of the entity.
     *
     * @return The y coordinate of the entity.
     */
    public int getPosY() {
        return this.posY;
    }

    /**
     * Sets the x coordinate of the entity.
     *
     * @param posX The new x coordinate of the entity.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Sets the y coordinate of the entity.
     *
     * @param posY The new y coordinate of the entity.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
}