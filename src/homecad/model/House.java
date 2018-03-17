/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

import homecad.model.exception.*;
import java.util.*;

/**
 *
 * @author satthuvdh
 *
 * House class gives all the essential method to interact with room, storey and house.
 *
 */
public class House implements Buildable {

    private ArrayList<Storey> storeyList = new ArrayList<Storey>();

    public House() {
    }
    /**
     *
     * @return the total size of all rooms in the storey which means the size
     * of the house
     */
    public int calculateSize() {
        Room[] rooms = getAllRooms();
        if (rooms == null || rooms.length == 0) {
            return 0;
        }
        int totalSize = 0;
        for (int i = 0; i < rooms.length; i++) {
            totalSize += rooms[i].calculateSize();
        }
        return totalSize;
    }
    /**
     *
     * @return the total cost of all rooms in a house which generate the total
     * cost of the house.
     */

    public int calculateCost() {
        Room[] rooms = getAllRooms();
        if (rooms == null || rooms.length == 0) {
            return 0;
        }
        int totalCost = 0;
        for (int i = 0; i < rooms.length; i++) {
            totalCost += rooms[i].calculateCost();
        }
        return totalCost;
    }
    /**
     * Clear all the items, exitpoints, rooms and storeis in the house
     * 
     */
    public void clearArea(){
        for (int i = 0; i < storeyList.size(); i++) {
            storeyList.get(i).clearArea();
        }
        storeyList.clear();
    }
   /**
    * Add a room to a storey, if the storey is not available, add the correspondent storey to house
    * @param in-the room  user want to add to the house
    * @throws StructuralException if the room user want to add is already exist
    * or the house user want to add does not have a support room below.
    */
    public void addRoom(Room in) throws StructuralException {
        if (CheckLowerStructuralSupport(in.getLocation()) == false) {
            throw new StructuralException("STRUCTURAL HOUSE SUPPORT ERROR");
        }
        if (findStorey(in.getLocation()) == null) {
            addStorey(new Storey(in.getLocation().getZ()));
        }
        findStorey(in.getLocation()).addRoom(in);
    }
    /**
     * Add a storey to the house
     * @param storey
     */
    private void addStorey(Storey storey) {
        storeyList.add(storey);
    }
    /**
     * Check whether the checking room is able to be added to the storey
     * @param reference of the checking room
     * @return true if the checking room does have a support room below
     */
    private boolean CheckLowerStructuralSupport(RoomReference ref) {
        return (getRoom(new RoomReference(ref.getX(), ref.getY(), ref.getZ() - 1)) != null || ref.getZ() == 1);
    }
    /**
     * Check whether the checking room is able to be removed from the storey
     * @param reference of the checking room
     * @return true if the checking room is not supporting any room above
     */
    private boolean CheckUpperStructuralSupport(RoomReference ref) {
        return (getRoom(new RoomReference(ref.getX(), ref.getY(), ref.getZ() + 1)) == null);
    }
    /**
     * Find the storey through a room reference
     * @param reference of the the room which is placed at the storey user is looking for
     * @return the storey user is looking for if find, otherwise return null
     */
    public Storey findStorey(RoomReference ref) {
        for (int i = 0; i < storeyList.size(); i++) {
            if (storeyList.get(i).getStoryNumber() == ref.getZ()) {
                return storeyList.get(i);
            }
        }
        return null;
    }
    /**
     *
     * Find the storey through a room reference
     * @param reference of the the room which is placed at the storey user is looking for
     * @return the storey user is looking for if find, otherwise return null
     */
    public Storey findStorey(int storey) {
        for (int i = 0; i < storeyList.size(); i++) {
            if (storeyList.get(i).getStoryNumber() == storey) {
                return storeyList.get(i);
            }
        }
        return null;
    }
    /**
     *
     * @return an array which contains all of the rooms object in the house
     */
    public Room[] getAllRooms() {
        ArrayList<AbstractRoom> rooms = new ArrayList<AbstractRoom>();
        for (int i = 0; i < storeyList.size(); i++) {
            if (storeyList.get(i).getAllRooms() != null) {
                rooms.addAll(Arrays.asList(storeyList.get(i).getAllRooms()));
            }
        }
        if (rooms.isEmpty()) {
            return null;
        }
        return rooms.toArray(new Room[rooms.size()]);
    }
    /**
     * Find a room through a reference of the room
     * @param reference of the room want to get
     * @return the room object if find, otherwise return null
     */
    public Room getRoom(RoomReference in) {
        Room[] rooms = getAllRooms();
        if (rooms == null || rooms.length == 0) {
            return null;
        }
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getLocation().equals(in)) {
                return rooms[i];
            }
        }
        return null;

    }
    /**
     *
     * @return an array which contains all of the storeis in the house
     */
    public Storey[] getStoreis() {
        Storey[] storeis = storeyList.toArray(new Storey[storeyList.size()]);
        return storeis;
    }
    /**
     * Delete a room from a storey
     * @param location of the room which the user intents to delete
     * @return the value of the deleted room
     * @throws StructuralException if the room is support another room so that
     * it cannot be deleted
     */
    public int removeRoom(RoomReference loc) throws StructuralException {
        if (CheckUpperStructuralSupport(loc) == false) {
            throw new StructuralException("STRUCTURAL HOUSE SUPPORT ERROR");
        }
        return findStorey(loc).removeRoom(loc);
    }
    /**
     * Delete a storey from a house
     * @param storeyNumber: the location of the storey
     */
    private void removeStorey(int storeyNumber) {
        storeyList.remove(findStorey(storeyNumber));
    }
}
