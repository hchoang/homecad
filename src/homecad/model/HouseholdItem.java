/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

/**
 *
 * @author satthuvdh
 * This class extends from Item class and provide all methods for HouseholdItem
 */
public class HouseholdItem extends Item {

    public HouseholdItem() {
    }

    public HouseholdItem(String name) {
        super(name);
    }

    public HouseholdItem(String name, int price) {
        super(name, price);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name+"-"+price;
    }

    
}
