package game.entities.enemies;

import game.entities.FightingEntity;
import game.entities.Player;

import java.util.Random;

/**
 * Represents a FightingEntity that can fight the player.
 */
public class Enemy extends FightingEntity implements Comparable<Enemy> {

    private Player player;

    private int attackChance;
    private int defendChance;
    private int stallChance;

    /**
     * Represents an Enemy object on the map.
     *
     * @param name       The name of the enemy.
     * @param appearance The appearance of the enemy.
     * @param player     The player object that is used to get the distance from this object to the player.
     * @param health     The starting and max health of the enemy.
     * @param power      The power level of the enemy.
     * @param armor      The armor level of the enemy.
     * @param posX       The x coordinate on the map.
     * @param posY       The y coordinate on the map.
     */
    Enemy(String name, char appearance, Player player, int health, int power, int armor, int posX, int posY) {
        super(name, appearance, health, power, armor, posX, posY);

        this.player = player;

        //Set the specific combat chances based on the name of the enemy
        switch (name) {

            case "Goblin":
                this.attackChance = Goblin.ATTACK_CHANCE;
                this.defendChance = Goblin.DEFEND_CHANCE;
                this.stallChance = Goblin.STALL_CHANCE;
                break;

            case "Skeleton":
                this.attackChance = Skeleton.ATTACK_CHANCE;
                this.defendChance = Skeleton.DEFEND_CHANCE;
                this.stallChance = Skeleton.STALL_CHANCE;
                break;

            case "Baby Dragon":
                this.attackChance = BabyDragon.ATTACK_CHANCE;
                this.defendChance = BabyDragon.DEFEND_CHANCE;
                this.stallChance = BabyDragon.STALL_CHANCE;
                break;

            default:
                this.attackChance = Dragon.ATTACK_CHANCE;
                this.defendChance = Dragon.DEFEND_CHANCE;
                this.stallChance = Dragon.STALL_CHANCE;
                break;
        }
    }

    /**
     * Gets the enemy's choice in the fight using different weights and a random formula.
     *
     * @return The enemy's choice in the fight using different weights.
     */
    public String aiChoice() {

        final Random ai = new Random();

        //Get the pseudo random roll
        int roll = ai.nextInt(100) + 1;

        if (roll <= this.attackChance) {
            return "attack";

        } else if (roll <= (this.attackChance + this.defendChance)) {
            return "defend";

        } else if (roll <= (this.attackChance + this.defendChance + this.stallChance)) {
            return "stall";

        } else {
            return "aiChoice(): This should never happen.";
        }
    }

    /**
     * Gets the amount of coins needed to give to the player when they kill an enemy. Goblins are 2 coins, skeletons are
     * 3 coins, baby dragons are 4 coins, and dragons are 6.
     *
     * @return The specific number of coins dependent on the type of enemy.
     */
    public int getCoinReward() {

        int coin;

        switch (getName()) {

            case "Goblin":
                coin = Goblin.COINS_GIVEN;
                break;

            case "Skeleton":
                coin = Skeleton.COINS_GIVEN;
                break;

            case "Baby Dragon":
                coin = BabyDragon.COINS_GIVEN;
                break;

            default:
                coin = Dragon.COINS_GIVEN;
                break;
        }
        return coin;
    }

    /**
     * Gets an integer value that is the result of a comparison between this enemy object and another enemy object.
     *
     * @param enemy The other enemy object being compared.
     * @return An integer value that is the result of the comparison.
     */
    @Override
    public int compareTo(Enemy enemy) {
        return Integer.compare(this.player.getDistanceFromAnEnemy(this), this.player.getDistanceFromAnEnemy(enemy));
    }
}