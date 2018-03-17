/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

import homecad.model.exception.AddtoRoomException;
import java.util.*;

/**
 *
 * @author satthuvdh
 * Room class represent a room in a house and extends AbstractRoom class
 * Room store all of its items and exit point to 2 ArrayList called itemList and exitPoint
 */
public class Room extends AbstractRoom {

    public static final int DEFAULT_HEIGHT = 5;
    public static final int DEFAULT_LENGTH = 5;
    public static final int DEFAULT_METER_COST = 10;
    public static final int DEFAULT_WIDTH = 5;
    private int height;
    private int length;
    private int width;
    private ArrayList<Item> itemList = new ArrayList();
    private ArrayList<ExitPoint> exitPoint = new ArrayList<ExitPoint>();

    public Room(RoomReference location, String name) {
        super(location, name);
        this.height = DEFAULT_HEIGHT;
        this.length = DEFAULT_LENGTH;
        this.width = DEFAULT_WIDTH;
    }

    public Room(RoomReference location, String name, int length, int width, int height) {
        this(location, name);
        this.height = height;
        this.length = length;
        this.width = width;
    }

    public Room() {
        super();
    }

    /**
     * Add an exit point to the room
     * Assump that user can't not add an exit point form one storey to another
     * or add exit point to 2 rooms that are not located next to each other
     * @param in: the exit point user wants to add
     */
    public void addExit(ExitPoint in) {
        ExitPoint[] ep = getExitList();
        if (this.getLocation().equals(in.getDestination())) {
            throw new AddtoRoomException("ADD EXIT POINT ERROR");
        }
        if (getExit(in.getName()) != null) {
            throw new AddtoRoomException("EXITPOITN EXIST ERROR");
        }
        if (ep != null || ep.length != 0) {
            for (int i = 0; i < ep.length; i++) {
                if (in.getDestination().equals(ep[i].getDestination())) {
                    throw new AddtoRoomException("EXITPOITN EXIST ERROR");
                }
            }
        }
        if (in.getDestination().getZ() != getLocation().getZ()) {
            throw new AddtoRoomException("CAN'T ADD EXITPOINT TO ANOTHER STOREY ERROR");
        }
        if ((in.getDestination().getX() > getLocation().getX() && in.getDestination().getY() > getLocation().getY())
                || (in.getDestination().getX() > getLocation().getX() && in.getDestination().getY() < getLocation().getY())
                || (in.getDestination().getX() < getLocation().getX() && in.getDestination().getY() < getLocation().getY())
                || (in.getDestination().getX() < getLocation().getX() && in.getDestination().getY() > getLocation().getY())
                || in.getDestination().getX() < getLocation().getX() - 1
                || in.getDestination().getX() > getLocation().getX() + 1
                || in.getDestination().getY() > getLocation().getY() + 1
                || in.getDestination().getY() < getLocation().getY() - 1) {

            throw new AddtoRoomException("CAN'T ADD EXITPOINT ERROR");
        }
        exitPoint.add(in);
    }

    /**
     * Add an Item to the room
     * Assump that each room can only has at most 9 items
     * and each item has unique name
     * @param item: item that user wants to add
     */
    public void addItem(Item item) {
        if (getItemList().length == 9) {
            throw new AddtoRoomException("ROOM IS FULL, CAN'T ADD MORE");
        }
        if (getItem(name) != null) {
            throw new AddtoRoomException("ITEM EXIST ERROR");
        }
        Item[] itemLocal = getItemList();
        if (itemLocal != null && itemLocal.length != 0) {
            for (int i = 0; i < itemLocal.length; i++) {
                if (item.getName().equals(itemLocal[i].getName())) {
                    throw new AddtoRoomException("ITEM EXIST ERROR");
                }
            }
        }

        itemList.add(item);
    }

    /**
     *
     * @return the total cost of the room which is generated by the basic cost
     * of the room plus the cost of all items in the room
     */
    @Override
    public int calculateCost() {
        int roomCost = calculateSize() * DEFAULT_METER_COST;
        Item[] item = getItemList();
        int itemCost = 0;
        for (int i = 0; i < item.length; i++) {
            itemCost += item[i].getPrice();
        }
        return roomCost + itemCost;
    }

    /**
     *
     * @return the size of the room in cubic meter
     */
    @Override
    public int calculateSize() {
        return height * width * length;
    }

    /**
     * Clear all exit points and items in the room
     */
    @Override
    public void clearArea() {
        exitPoint.clear();
        itemList.clear();
    }

    /**
     * Get the exit point through its name
     * @param name
     * @return the exit point if find, otherwise return null
     */
    public ExitPoint getExit(String name) {
        ExitPoint[] ep = getExitList();
        for (int i = 0; i < ep.length; i++) {
            if (ep[i].getName().equals(name)) {
                return ep[i];
            }
        }
        return null;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    @Override
    public RoomReference getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return an ExitPoint array which contains all exit points in the room
     */
    public ExitPoint[] getExitList() {
        ExitPoint[] ep = exitPoint.toArray(new ExitPoint[exitPoint.size()]);
        return ep;
    }

    /**
     * Get an item through its name
     * @param name: the name of the item
     * @return an Item Object if find, otherwise return null
     */
    public Item getItem(String name) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(name)) {
                return itemList.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @return an Item array which contains all the items in the house
     */
    public Item[] getItemList() {
        return itemList.toArray(new Item[itemList.size()]);
    }

    /**
     * Remove an exit point from the room
     * @param exitName: the name of the exit point
     * @return true if the remove process success, otherwise return false
     */
    public boolean removeExit(String exitName) {
        return exitPoint.remove(getExit(exitName));
    }

    /**
     * Remove an item from the room
     * @param itemName: the name of the item
     * @return true if the remove process success, otherwise return false
     */
    public boolean removeItem(String itemName) {
        return (itemList.remove(getItem(itemName)));
    }

    /**
     *
     * @return a string which in the format <item 1>,<item 2>,...,<item n>
     * <item n> is in the format itemname-itemprice for HouseholdItem or
     * itemname-itemprice-istallationcost for ElectronicAppliance
     */
    private StringBuffer listItem() {
        Item[] item = getItemList();
        StringBuffer tempList = new StringBuffer();
        for (int i = 0; i < item.length; i++) {
            tempList = tempList.append(",").append(item[i].toString());
        }
        tempList.delete(0, 1);
        return tempList;
    }

    /**
     *
     * @return a string which contains the name of all exit points.
     * The string is in the format <ExitPoint 1 name>,<ExitPoint 2 name>,...,<ExitPoint n name>
     */
    private StringBuffer listExit() {
        ExitPoint[] exit = getExitList();
        StringBuffer tempList = new StringBuffer();
        for (int i = 0; i < exit.length; i++) {
            tempList = tempList.append(",").append(exit[i].toString());
        }
        tempList.delete(0, 1);
        return tempList;
    }

    @Override
    public String toString() {
        return getLocation().toString() + ":" + length + "," + width + "," + height + ":" + name + ":" + listItem() + ":" + listExit();
    }
}
