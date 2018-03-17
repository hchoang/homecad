/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

/**
 *
 * @author satthuvdh
 *
 *
 */
public class ExitPoint {

    private String name;
    private RoomReference destination;

    public ExitPoint(String name, RoomReference destination) {
        this.name = name.replaceAll("([:,])", "");
        this.destination = destination;
    }

    public RoomReference getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }
    /**
     * Compare 2 Exit point together,
     * @param in
     * @return true if they are equal and false if not
     */
    public boolean equals(ExitPoint in) {
        return (this.destination.equals(in.destination) && this.name.equals(name))?true:false;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
