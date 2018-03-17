/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view.popup;

import homecad.model.*;
import java.awt.*;
import javax.swing.*;
import homecad.view.*;
import java.awt.event.*;
import java.util.*;

/**
 * This pop up is use to remove an item
 * from a room, user choose a room and then
 * item from drop down list
 * down list
 * @author Cat Hoang Huy
 */
public class RemoveItemPopup extends JFrame {

    private JPanel panel;
    private JComboBox itemList = new JComboBox();
    private JComboBox roomListCB;
    private JButton removeItemBT = new JButton("Remove");

    public RemoveItemPopup() {
        actionPerformed();
        panel = new JPanel();
        setTitle("CHOOSE AN ITEM");
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select an Room:"));
        panel.add(roomListCB);
        panel.add(new JLabel("Select Item:"));
        panel.add(itemList);
        panel.add(removeItemBT);

        add(panel);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();
    }

    private void actionPerformed() {
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
        itemList = new JComboBox(MainView.model.getRoom((RoomReference) roomListCB.getSelectedItem()).getItemList());
        roomListCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                itemList = new JComboBox(MainView.model.getRoom((RoomReference) roomListCB.getSelectedItem()).getItemList());
                setVisible(false);
                itemList.updateUI();

                panel.removeAll();
                panel.add(new JLabel("Select an Room:"));
                panel.add(roomListCB);
                panel.add(new JLabel("Select Item:"));
                panel.add(itemList);
                panel.add(removeItemBT);
                add(panel);
                setVisible(true);
                pack();
            }
        });
        removeItemBT.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (itemList.getSelectedItem() != null && !(itemList.getSelectedItem().equals(""))) {
                    MainView.model.removeItem((RoomReference) roomListCB.getSelectedItem(), ((Item) itemList.getSelectedItem()).getName());

                } else {
                    JOptionPane.showMessageDialog(null, "THERE ARE NO ITEM TO REMOVE");
                }
                dispose();
            }
        });
    }
}
