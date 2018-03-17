/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view.popup;

import homecad.model.*;
import homecad.model.exception.*;
import homecad.view.MainView;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * this frame will display when user click to Edit>Add Exit Point
 * user choose the location and enter the name of the exit door to add
 * @author Cat Hoang Huy
 */
public class AddExitPointPopup extends JFrame {

    private JComboBox listRoom1, listRoom2;
    private JTextField exitPointName = new JTextField(15);

    public AddExitPointPopup() {
        if (MainView.model.getAllRooms() == null) {
            listRoom1 = new JComboBox();
            listRoom2 = new JComboBox();
        } else {
            Room[] room1 = MainView.model.getAllRooms();
            ArrayList<RoomReference> roomLocation1 = new ArrayList<RoomReference>();
            ArrayList<RoomReference> roomLocation2 = new ArrayList<RoomReference>();
            for (int i = 0; i < room1.length; i++) {
                roomLocation1.add(room1[i].getLocation());
                roomLocation2.add(room1[i].getLocation());
            }

            listRoom1 = new JComboBox(roomLocation1.toArray());
            Room[] room2 = MainView.model.getAllRooms();
            listRoom2 = new JComboBox(roomLocation2.toArray());
        }
        JButton addExitPointBT = new JButton("Add");
        setTitle("ADD EXIT POINT");
        setLayout(new FlowLayout());
        add(new JLabel("Source:"));
        add(listRoom1);
        add(new JLabel("Destination:"));
        add(listRoom2);
        add(new JLabel("Exit Point Name:"));
        add(exitPointName);
        add(addExitPointBT);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();

        addExitPointBT.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    MainView.model.addExitPoint(((RoomReference) listRoom1.getSelectedItem()), new ExitPoint(exitPointName.getText(), ((RoomReference) listRoom2.getSelectedItem())));
                } catch (AddtoRoomException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } finally {
                    dispose();
                }

            }
        });



    }
}
