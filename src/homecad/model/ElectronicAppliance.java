/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

/**
 *
 * @author satthuvdh
 *
 * this class extends the item class and override most of the method of Item as
 * besides the basic cost of an Item, ElectronicAppliance require an installation
 * cost for each item the owner buy.
 */
public class ElectronicAppliance extends Item {

    public static final int INSTALLATION_COST=15;
    private int installCost;

    public ElectronicAppliance() {
    }

    public ElectronicAppliance(String name) {
        super(name);
        this.installCost=INSTALLATION_COST;
    }

    public ElectronicAppliance(String name, int price) {
        this(name, price,INSTALLATION_COST);
    }

    public ElectronicAppliance(String name, int price, int installCost) {
        super(name, price);
        this.installCost = installCost;

    }

    public int getInstallCost() {
        return installCost;
    }


    public int getPrice() {
        return price+installCost;
    }

    @Override
    public String toString() {
        return name+"-"+price+"-"+installCost;
    }

}
