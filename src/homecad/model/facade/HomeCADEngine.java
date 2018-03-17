/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model.facade;

import homecad.model.*;
import homecad.model.exception.*;
import java.util.Observable;

/**
 *
 * @author satthuvdh
 */
public class HomeCADEngine extends Observable implements
        HomeCADModel {

    private int currentStorey = 1;
    private House house = new House();
    private Owner owner;
    private Room currentRoom = new Room();

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        setChanged();
        notifyObservers();
    }

    public HomeCADEngine() {
        this.owner = new Owner();
    }

    public HomeCADEngine(String name, int intialBudget) {
        this.owner = new Owner(name, intialBudget);
    }

    public int getCurrentStorey() {
        return currentStorey;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentStorey(int currentStorey) {
        this.currentStorey = currentStorey;
        setChanged();
        notifyObservers();
    }

    public Owner getOwner() {
        return owner;
    }

    public int calculateHouseSize() {
        return house.calculateSize();
    }

    public int calculateStoreySize(int storey) {
        return house.findStorey(storey).calculateSize();
    }

    public int calculateRoomCost(RoomReference location) {
        return getRoom(location).calculateCost();
    }

    public int calculateHouseCost() {
        return house.calculateCost();
    }

    public int calculateStoreyCost(int storey) {
        return house.findStorey(storey).calculateCost();
    }

    public Room getRoom(RoomReference location) {
        return house.getRoom(location);
    }

    public Room[] getAllRooms() {
        return house.getAllRooms();
    }

    public Room[] getStoreyRooms(int storey) {
        return house.findStorey(storey).getAllRooms();
    }

    public Storey[] getStoreys() {
        return house.getStoreis();
    }

    /**
     * Add an room to the house
     * @param room: the room Object which user wants to add
     * @throws StructuralException if the added room does not satisfy the adding
     * conditions
     * @throws FinanceException if the cost of the added room is too much for
     * the owner to build
     */
    public void addRoom(Room room) throws StructuralException, FinanceException {
        if (owner.getAvailableBudget() < room.calculateCost()) {
            throw new FinanceException("INSUFFICIENT BUDGET ERROR");
        }
        house.addRoom(room);
        owner.decreaseBudget(room.calculateCost());
        setChanged();
        notifyObservers();
    }

    /**
     * Remove a room from a house
     * @param location: the reference of the removed room
     * @throws StructuralException if the removed room does not satisfy the removing
     * condition
     */
    public void removeRoom(RoomReference location) throws StructuralException {
        if (house.getRoom(location) == null) {
            throw new StructuralException("ROOM DOES NOT EXIST ERROR");
        }
        Item[] items = house.getRoom(location).getItemList();
        for (int i = 0; i < items.length; i++) {
            this.removeItem(location, items[i].getName());
        }
        ExitPoint[] exitPoint = house.getRoom(location).getExitList();
        for (int i = 0; i < exitPoint.length; i++) {
            this.removeExitPoint(location, exitPoint[i].getName());
        }
        owner.increaseBudget(house.removeRoom(location));
        setChanged();
        notifyObservers();
    }

    /**
     * Add an item to a house
     * @param location: the reference of the room item is added to
     * @param item: the item user wants to add
     * @return true if the adding process is success, otherwise return false
     * @throws FinanceException if the owner is un-affordable for the item
     */
    public boolean addItem(RoomReference location, Item item) throws FinanceException {
        if (owner.getAvailableBudget() < item.getPrice()) {
            throw new FinanceException("INSUFFICIENT BUDGET ERROR");
        }
        Room room = house.getRoom(location);
        if (room == null) {
            return false;
        }
        room.addItem(item);
        owner.decreaseBudget(item.getPrice());
        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * Remove an item from a house
     * @param location of the room which user want to remove item
     * @param name: the name of the removed item
     * @return true if the remove process is success, otherwise return false
     */
    public boolean removeItem(RoomReference location, String name) {
        Room room = house.getRoom(location);
        if (room == null) {
            return false;
        }
        Item item = room.getItem(name);
        if (room.removeItem(name)) {

            owner.increaseBudget(item.getPrice());
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    /**
     * Add an exit point to the room in the house
     * @param location: the reference of the room which user wants to add exit point
     * @param exit: the added exit point
     * @return true if the adding process success, otherwise return false
     */
    public boolean addExitPoint(RoomReference location, ExitPoint exit) {
        Room room = house.getRoom(location);
        if (room == null || house.getRoom(exit.getDestination()) == null) {
            return false;
        }
        room.addExit(exit);
        house.getRoom(exit.getDestination()).addExit(new ExitPoint(exit.getName(), room.getLocation()));
        setChanged();
        notifyObservers();
        return true;

    }

    /**
     * Remove an exit point from a room in the house
     * @param location: the location of the room
     * @param name: the name of the exit point which user wants to remove
     * @return true if the remove process is success, otherwise return false
     */
    public boolean removeExitPoint(RoomReference location, String name) {
        Room room = house.getRoom(location);
        if (room == null) {
            return false;
        }
        if (room.getExit(name) != null) {
            boolean checkRemove = house.getRoom(room.getExit(name).getDestination()).removeExit(name) && room.removeExit(name);
            setChanged();
            notifyObservers();
            return checkRemove;
        }
        return false;

    }

    /**
     * Reset all to the beginning, clear all items, exit points, rooms, storey
     * all reset all info of the owner
     */
    public void reset() {
        house.clearArea();
        owner.resetBudget();
        setChanged();
        notifyObservers();
    }
}
