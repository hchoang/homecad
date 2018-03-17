/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

/**
 *
 * @author satthuvdh
 * Owner class represent owner of the house
 */
public class Owner {

    public static int DEFAULT_BUDGET = 100000;
    private String name;
    private int totalBudget;
    private int availableBudget;

    public Owner() {
        this(null,DEFAULT_BUDGET);
    }

    public Owner(String name) {
        this(name, DEFAULT_BUDGET);
    }

    public Owner(String name, int Budget) {
        this.name = name;
        this.totalBudget = Budget;
        this.availableBudget = Budget;
    }

    public int getAvailableBudget() {
        return availableBudget;
    }

    public String getName() {
        return name;
    }

    public int getTotalBudget() {
        return totalBudget;
    }

    public int decreaseBudget(int cost) {
        availableBudget -= cost;
        return availableBudget;
    }

    public int increaseBudget(int cost) {
        availableBudget += cost;
        return availableBudget;
    }

    public void resetBudget() {
        availableBudget = totalBudget;
    }

    @Override
    public String toString() {
        return name + ":" + totalBudget + ":" + availableBudget;
    }
}
