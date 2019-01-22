package game.entities.items;

public class WeaponPickUp extends Item {

    /**
     * Represents a Weapon object that can be picked up on the map.
     *
     * @param appearance The appearance of the weapon.
     * @param posX       The x coordinate on the map.
     * @param posY       The y coordinate on the map.
     */
    public WeaponPickUp(char appearance, int posX, int posY) {
        super("Weapon Pickup", appearance, posX, posY);
    }
}