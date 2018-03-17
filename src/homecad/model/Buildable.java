/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.model;

import homecad.model.exception.*;

/**
 *
 * @author satthuvdh
 *
 * Buildable interface allow classes which implement it able to calculate its size,
 * cost and clear itself.
 */
public interface Buildable {

    int calculateSize();

    int calculateCost();

    void clearArea() throws HomeCADException;
}
