/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package homecad.model.exception;

/**
 *
 * @author satthuvdh
 * This Exception will be throws if the added item or exit point is already exist
 */
public class AddtoRoomException extends RuntimeException{

    public AddtoRoomException(String message) {
        super(message);
    }

    public AddtoRoomException() {
        super();
    }

}
