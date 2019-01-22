package game.data;

/**
 * Holds all of the random information that starts getting stored when level 2 is created and gets reset every level
 * after.
 */
public abstract class RandomData {

    private static int[] winLocation;
    private static int[] playerStartLocation;
    private static int[] buildingLocation;

    /**
     * Gets the win location of the random map.
     *
     * @return The win location of the random map.
     */
    public static int[] getWinLocation() {
        return RandomData.winLocation;
    }

    /**
     * Sets the win location on the random map.
     *
     * @param winLocation The win location of the random map.
     */
    public static void setWinLocation(int[] winLocation) {
        RandomData.winLocation = winLocation;
    }

    /**
     * Gets the player location of the random map.
     *
     * @return The player location of the random map.
     */
    public static int[] getPlayerStartLocation() {
        return RandomData.playerStartLocation;
    }

    /**
     * Sets the player's starting location on the random map.
     *
     * @param playerStartLocation The player's starting location of the random map.
     */
    public static void setPlayerStartLocation(int[] playerStartLocation) {
        RandomData.playerStartLocation = playerStartLocation;
    }

    /**
     * Gets the building location of the random map.
     *
     * @return The building location of the random map.
     */
    public static int[] getBuildingLocation() {
        return RandomData.buildingLocation;
    }

    /**
     * Sets the building location on the random map.
     *
     * @param buildingLocation The building location of the random map.
     */
    public static void setBuildingLocation(int[] buildingLocation) {
        RandomData.buildingLocation = buildingLocation;
    }

    /*Singleton method (of is from the last two letters of instanceof)
    Used when keeping things non-static but you only want to create one instance of the class, so you would use this
    method to get access to the class.
    public static LevelData of(int level) {

       if (level == 1) {

            if (level1Data == null) {
                level1Data = new Level1Data();
            }
            return level1Data;

        } else {

            if (randomData == null) {
                randomData = new RandomData();
            }
            return randomData;
        }
    }
    */
}