package homecad.model.facade;

import homecad.model.*;
import homecad.model.exception.*;

public interface HomeCADModel {

    public Owner getOwner();

    public int calculateHouseSize();

    public int calculateStoreySize(int storey);

    public int calculateRoomCost(RoomReference location);

    public int calculateHouseCost();

    public int calculateStoreyCost(int storey);

    public Room getRoom(RoomReference location);

    public Room[] getAllRooms();

    public Room[] getStoreyRooms(int storey);

    public void addRoom(Room room) throws StructuralException, FinanceException;

    public void removeRoom(RoomReference location) throws StructuralException;

    public boolean addItem(RoomReference location, Item item) throws FinanceException;

    public boolean removeItem(RoomReference location, String name);

    public boolean addExitPoint(RoomReference location, ExitPoint exit);

    public boolean removeExitPoint(RoomReference location, String name);

    public void reset();

}
