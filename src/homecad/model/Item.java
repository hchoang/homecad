/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

/**
 *
 * @author satthuvdh
 * Abstract class Item is a blueprint for ElectronicAppliance and HouseholdItem classes
 * to implement
 */
public abstract class Item {

    public static final int DEFAULT_ITEM_COST=10;
    protected String name;
    protected int price;

    public Item() {
    }

    public Item(String name) {
       this(name,DEFAULT_ITEM_COST);

    }

    public Item(String name, int price) {
        this.name = name.replaceAll("([:,])", "");
        this.price = price;
    }

    public String getName() {
        return name;
    }

    abstract public int getPrice();

    @Override
    abstract public String toString();

    public int compareTo(Item in) {
        if (this.price>in.getPrice()) {
            return 1;
        }else{
            if (this.price==in.getPrice()) {
                return 0;
            }
            return -1;
        }
    }
}
