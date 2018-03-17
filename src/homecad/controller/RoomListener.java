/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.controller;

import homecad.model.*;
import homecad.model.exception.*;
import homecad.model.facade.HomeCADEngine;
import homecad.view.MainView;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 * This will handle the action when user click on a panel in
 * room grid
 * @author Cat Hoang Huy
 */
public class RoomListener extends MouseAdapter {

    private HomeCADEngine model = new HomeCADEngine();
    int x;
    int y;

    public RoomListener(HomeCADEngine model, int x, int y) {
        this.model = model;
        this.x = x;
        this.y = y;


    }

    /**
     * this method will check whether user double clicks or single click on the
     * room panel
     * If at the location user double click is no room, the program will ask the user
     * to add a room otherwise will as the user to delete the room
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        RoomReference ref = new RoomReference(x, y, this.model.getCurrentStorey());
        if (e.getClickCount() == 2) {
            if (model.getRoom(ref) != null) {
                removeRoom(ref);
            } else {
                addRoom(ref);
            }
        }
        model.setCurrentRoom(model.getRoom(ref));
    }

    /**
     * this method will show a pop up to get user input for a new room that
     * user want to add
     * @param ref RoomReference the location of the adding room
     */
    private void addRoom(RoomReference ref) {
        String name = JOptionPane.showInputDialog("Enter the Room Name:");



        if (name != null) {
            try {
                model.addRoom(new Room(ref, name));



            } catch (HomeCADException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

    }

    /**
     * this method will pop up to confirm that user want to delete a room at the
     * location where user double clicks
     * @param ref
     */
    private void removeRoom(RoomReference ref) {
        int choice = JOptionPane.showConfirmDialog(null, "Delete This Room?");


        if (choice == 0) {
            try {
                MainView.model.removeRoom(ref);


            } catch (HomeCADException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());

            }
        }
    }
}
