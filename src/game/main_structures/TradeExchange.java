package game.main_structures;

import game.entities.*;
import game.entities.buildings.Building;
import game.entities.items.Item;

import java.util.Scanner;

class TradeExchange {

    private Player modifiedPlayer;
    private Building modifiedBuilding;
    private boolean done = false;

    /**
     * The constructor for the TradeExchange object which represents a period of turns where the player is buying or
     * selling items.
     *
     * @param player   The player object participating in the exchange.
     * @param building The building object that is participating in the exchange.
     */
    TradeExchange(Player player, Building building) {
        this.modifiedPlayer = player;
        this.modifiedBuilding = building;
    }

    /**
     * Starts the exchange between the player and the specific building.
     */
    void startExchange() {

        final Scanner tempScanner = new Scanner(System.in);

        while (!this.done) {

            boolean sell = this.modifiedPlayer.getDamagePotionCount() > 0 || this.modifiedPlayer.getHealthPotionCount() > 0;
            boolean buy = this.modifiedPlayer.getCoinCount() >= Item.MINIMUM_ITEM_COST;
            boolean shopCanSell = this.modifiedBuilding.getHealthPotionsAvailable() > 0 || this.modifiedBuilding.getDamagePotionsAvailable() > 0;

            System.out.println();
            System.out.println("Currently there are " + this.modifiedBuilding.getDamagePotionsAvailable() +
                    " damage potions available and " + this.modifiedBuilding.getHealthPotionsAvailable() + " health potions available.");
            System.out.println("You have " + this.modifiedPlayer.getHealthPotionCount() + " health potions and " + this.modifiedPlayer.getDamagePotionCount() + " damage potions.");
            System.out.println("Also you have " + this.modifiedPlayer.getCoinCount() + " coins.");

            //If the shop can sell and the player can buy, or if you player can sell
            if ((shopCanSell && buy) || sell) {

                //If the player can buy and sell
                if (buy && sell) {
                    System.out.println();
                    System.out.println("What would you like to do?");
                    System.out.println("(Options: [Sell] [Buy] [Leave])");
                    System.out.println("Use s, b, or l respectively for the given options.");
                    choiceForBuyAndSell(tempScanner);
                    System.out.println();

                    //If the player can only sell
                } else if (sell) {
                    System.out.println();
                    System.out.println("What would you like to do?");
                    System.out.println("(Options: [Sell] [Leave])");
                    System.out.println("Enter s or l for convenience to the respective options.");
                    choiceForSell(tempScanner);
                    System.out.println();

                    //If the player can only buy
                } else {
                    System.out.println();
                    System.out.println("What would you like to do?");
                    System.out.println("(Options: [Buy] [Leave])");
                    System.out.println("Use b or l respectively for the given options.");
                    choiceForBuy(tempScanner);
                    System.out.println();
                }

            } else {
                System.out.println("Sorry you can't buy or sell anything.");
            }
        }
    }

    /**
     * Handles the outcome if the player can buy or sell.
     *
     * @param tempScanner The scanner that is taking input.
     */
    private void choiceForBuyAndSell(Scanner tempScanner) {

        String input = tempScanner.nextLine().trim();
        System.out.println();

        if (input.equalsIgnoreCase("Sell")) {

            System.out.println("What would you like to sell?");
            System.out.println("(Options: [Damage Potion] [Health Potion] [Cancel])");
            System.out.println("Enter in d, h, or c for convenience to the respective options.");

            input = tempScanner.nextLine().trim();

            choiceForPotionSell(input);

        } else if (input.equalsIgnoreCase("Buy")) {

            System.out.println("What would you like to buy?");
            System.out.println("(Options: [Damage Potion] [Health Potion] [Cancel])");
            System.out.println("Enter d, h, or c for convenience to the respective options.");

            input = tempScanner.nextLine().trim();

            choiceForPotionBuy(input);

        } else if (input.equalsIgnoreCase("Leave")) {

            System.out.println("See you later.");
            this.done = true;

        } else {
            System.out.println("That isn't a valid option.\nPlease try again.");
        }
    }

    /**
     * Handles the outcome if the player can only sell.
     *
     * @param tempScanner The scanner that is taking input.
     */
    private void choiceForSell(Scanner tempScanner) {

        String input = tempScanner.nextLine().trim();

        if (input.equalsIgnoreCase("Sell") || input.equalsIgnoreCase("S")) {

            System.out.println("What would you like to sell?");
            System.out.println("(Options: [Damage Potion] [Health Potion] [Cancel])");
            System.out.println("Enter in d, h, or c for convenience to the respective options.");

            input = tempScanner.nextLine().trim();

            choiceForPotionSell(input);

        } else if (input.equalsIgnoreCase("Leave") || input.equalsIgnoreCase("L")) {

            System.out.println("See you later.");
            this.done = true;

        } else {
            System.out.println("That isn't a valid option.\nPlease try again.");
        }
    }

    /**
     * Handles the outcome if the player can only buy.
     *
     * @param tempScanner The scanner that is taking input.
     */
    private void choiceForBuy(Scanner tempScanner) {

        String input = tempScanner.nextLine().trim();

        if (input.equalsIgnoreCase("Buy") || input.equalsIgnoreCase("B")) {

            System.out.println("What would you like to buy?");
            System.out.println("(Options: [Damage Potion] [Health Potion] [Cancel])");
            System.out.println("Enter in d, h, or c for convenience to the respective options.");

            input = tempScanner.nextLine().trim();

            choiceForPotionBuy(input);

        } else if (input.equalsIgnoreCase("Leave") || input.equalsIgnoreCase("L")) {

            System.out.println("See you later.");
            this.done = true;

        } else {
            System.out.println("That isn't a valid option.\nPlease try again.");
        }
    }

    /**
     * Handles the outcome if a player wants to buy.
     *
     * @param input The specific potion the player wants to buy.
     */
    private void choiceForPotionBuy(String input) {

        if (input.equalsIgnoreCase("DamagePotion") || input.equalsIgnoreCase("D")) {

            this.modifiedPlayer.changeCoinCount(-Item.POTION_COST);
            this.modifiedPlayer.changeDamagePotionCount(1);
            this.modifiedBuilding.changeDamagePotionsAvailable(-1);
            System.out.println("Your coins have been deducted and you have been given a Damage Potion.");

        } else if (input.equalsIgnoreCase("HealthPotion") || input.equalsIgnoreCase("H")) {

            this.modifiedPlayer.changeCoinCount(-Item.POTION_COST);
            this.modifiedPlayer.changeHealthPotionCount(1);
            this.modifiedBuilding.changeHealthPotionsAvailable(-1);
            System.out.println("Your coins have been deducted and you have been given a Health Potion.");

        } else if (input.equalsIgnoreCase("Cancel") || input.equalsIgnoreCase("C")) {

            System.out.println("Canceled");

        } else {
            System.out.println("That isn't a valid choice.\nPlease try again.");
        }
    }

    /**
     * Handles the outcome if a player wants to sell.
     *
     * @param input The specific potion the player wants to sell.
     */
    private void choiceForPotionSell(String input) {

        if (input.equalsIgnoreCase("DamagePotion") || input.equalsIgnoreCase("d")) {

            this.modifiedPlayer.changeCoinCount(Item.COINS_GIVEN_FOR_POTION);
            this.modifiedPlayer.changeDamagePotionCount(-1);
            this.modifiedBuilding.changeDamagePotionsAvailable(1);
            System.out.println("Your Damage Potion has been removed and you have been given 2 coins.");

        } else if (input.equalsIgnoreCase("HealthPotion") || input.equalsIgnoreCase("h")) {

            this.modifiedPlayer.changeCoinCount(Item.COINS_GIVEN_FOR_POTION);
            this.modifiedPlayer.changeHealthPotionCount(-1);
            this.modifiedBuilding.changeHealthPotionsAvailable(1);
            System.out.println("Your Health Potion has been removed and you have been given 2 coins.");

        } else if (input.equalsIgnoreCase("Cancel") || input.equalsIgnoreCase("c")) {

            System.out.println("Canceled");

        } else {
            System.out.println("That isn't a valid item.\nPlease try again.");
        }
    }

    /**
     * Gets the player object from after the exchange.
     *
     * @return The player object from after the exchange.
     */
    Player getModifiedPlayer() {
        return this.modifiedPlayer;
    }
}