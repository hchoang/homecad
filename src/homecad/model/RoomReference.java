package homecad.model;
/**
 *
 * @author satthuvdh
 * RoomReference represent the location of an room
 */
public class RoomReference {

    private int x;
    private int y;
    private int z;

    // default ref (top-left corner of level 1)
    public RoomReference() {
        this(1, 1, 1);
    }

    public RoomReference(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    // hint: the following methods could be helpful when using
    // RoomReferences as keys in the HashMap containing Room objects
    // as is done in the example solution i.e. HashMap<RoomReference, AbstractRoom> space;
    /**
     * Compare a RoomReference with another object
     * @param in
     * @return false if compared object is not instance of RoomReference of three dimensions
     * are not equal, otherwise return true
     */
    @Override
    public boolean equals(Object in) {
        if (in instanceof RoomReference) {
            RoomReference rf = (RoomReference) in;
            return (x==rf.getX()&&y==rf.getY()&&z==rf.getZ())?true:false;
        }
        return false;

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.x;
        hash = 19 * hash + this.y;
        hash = 19 * hash + this.z;
        return hash;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
