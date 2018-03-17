/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

/**
 *
 * @author satthuvdh
 *
 * This method is the blueprint of the Room Class, which provides most of the
 * basic method which a room need to have to complete the requirement of this project
 */
public abstract class AbstractRoom implements Buildable {

    RoomReference location;
    protected String name;

    public AbstractRoom() {
    }

    public AbstractRoom(RoomReference location, String name) {
        this.location = location;
        this.name = name.replaceAll("([:,])", "");
    }

    public abstract int calculateCost();

    public abstract int calculateSize();

    public abstract void clearArea();
    @Override
    public abstract String toString();

    public RoomReference getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

}
