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
 * Storey represent a storey in a house
 * Storey implements Buildable and use HashMap to store all room inside it, which
 * use room reference as the key.
 */
public class Storey implements Buildable {

    public static int DEFAULT_STOREY_NUMBER;
    private int storey_number;
    private HashMap<RoomReference,AbstractRoom> space = new HashMap<RoomReference, AbstractRoom>();

    public Storey() {
        this.storey_number=DEFAULT_STOREY_NUMBER;
    }

    public Storey(int storey_number) {
        this.storey_number = storey_number;
    }
    /**
     * Add a room to the storey
     * @param in: the room which user want to add
     * @throws StructuralException if the added room is invalid or the location
     * user want to add is already occupied
     *
     */
    public void addRoom(Room in) throws StructuralException {
        if (!validReference(in.getLocation())) {
            throw new StructuralException("STRUCTURAL HOUSE ERROR");
        }
        if (this.getRoom(in.getLocation()) != null) {
            throw new StructuralException("DUPLICATE ROOM ERROR");
        }
        space.put(in.getLocation(), in);
    }
    /**
     *
     * @return the total size of all rooms in that storey
     */
    public int calculateSize() {
        Room[] rooms = getAllRooms();
        if (rooms == null || rooms.length == 0) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < rooms.length; i++) {
            total = total + rooms[i].calculateSize();
        }
        return total;
    }
    /**
     *
     * @return the total cost of all rooms in the storey
     */
    public int calculateCost() {
        Room[] rooms = getAllRooms();
        if (rooms == null || rooms.length == 0) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < rooms.length; i++) {
            total = total + rooms[i].calculateCost();
        }
        return total;
    }
    /**
     * Remove all item, exit point, room in the storey
     */
    public void clearArea() {
        RoomReference[] rr = space.keySet().toArray(new RoomReference[space.size()]);
        for (int i = 0; i < rr.length; i++) {
            space.get(rr[i]).clearArea();
        }
        space.clear();
    }
    /**
     *
     * @return an Room array which contains all room in the storey
     */
    public Room[] getAllRooms() {
        return (space.isEmpty())?null:space.values().toArray(new Room[space.size()]);
    }
    /**
     * Get a room from a storey
     * @param in: the reference of the room user wants to get
     * @return an room object if find, other wise return null
     */
    public AbstractRoom getRoom(RoomReference in) {
        return space.get(in);
    }

    public int getStoryNumber() {
        return storey_number;
    }
    /**
     * Remove a room from a storey
     * @param in: reference of the room user wants to remove
     * @return the cost value of the removed room
     */
    public int removeRoom(RoomReference in) {
        Room room = (Room) space.get(in);
        if (room != null) {
            int cost = room.calculateCost();
            space.remove(in);
            return cost;
        }
        return 0;
    }
    /**
     * Check whether the room is already exist
     * @param in
     * @return true if there is no room at given location, otherwise return false
     */
    public boolean roomsExits(RoomReference in) {      
        return (this.getRoom(in)==null)?true:false;
    }
    /**
     * Check whether the given reference is valid or not
     * @param in
     * @return true if valid, false if not
     */
    private boolean validReference(RoomReference in) {
        return (in.getX() <= 0 || in.getY() <= 0 || in.getZ() <= 0)?false:true;
    }

    @Override
    public String toString() {
        return ""+storey_number;
    }

}
