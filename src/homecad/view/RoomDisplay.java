/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view;

import homecad.controller.RoomListener;
import homecad.model.facade.HomeCADEngine;
import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

/**
 * This panel will display all the room in one specific storey
 * @author Cat Hoang Huy
 */
public class RoomDisplay extends JPanel implements Observer {

    private JPanel[][] room = new JPanel[7][7];
    private int current = 1;

    /**
     * this update method will repaint all of the room panel in the
     * main panel, it will check whether at a specific location,
     * there is a room or not
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        current = ((HomeCADEngine) o).getCurrentStorey();
        removeAll();
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room.length; j++) {
                room[i][j] = new RoomPanel(i + 1, j + 1, current);
                room[i][j].addMouseListener(new RoomListener(MainView.model, i + 1, j + 1));
                add(room[i][j]);
            }
        }
    }

    public RoomDisplay() {
        setLayout(new GridLayout(room.length, room.length));
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room.length; j++) {
                room[i][j] = new RoomPanel(i + 1, j + 1, current);
                room[i][j].addMouseListener(new RoomListener(MainView.model, i + 1, j + 1));
                add(room[i][j]);
            }
        }
    }
}
