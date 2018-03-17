/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view.popup;

import homecad.model.*;
import homecad.model.exception.*;
import homecad.view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * This pop up is use to remove a room
 * from a house, user choose a room from drop down list
 * 
 * @author Cat Hoang Huy
 */
public class RemoveRoomPopup extends JFrame {

    private JComboBox roomListCB;

    public RemoveRoomPopup() {
        if (MainView.model.getAllRooms() == null || MainView.model.getAllRooms().length == 0) {
            roomListCB = new JComboBox();
        } else {
            Room[] roomList = MainView.model.getAllRooms();
            ArrayList<RoomReference> rooms = new ArrayList<RoomReference>();
            for (int i = 0; i < roomList.length; i++) {
                rooms.add(roomList[i].getLocation());
            }
            roomListCB = new JComboBox(rooms.toArray());
        }
        JButton remove = new JButton("Remove Room");

        setTitle("REMOVE ROOM");
        setVisible(true);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        add(remove);
        add(roomListCB);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();
        remove.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                RoomReference ref = (RoomReference) roomListCB.getSelectedItem();

                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?");
                if (choice == 0) {
                    try {
                        MainView.model.removeRoom(ref);

                    } catch (HomeCADException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

                dispose();
            }
        });

    }
}
