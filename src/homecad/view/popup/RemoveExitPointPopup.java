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
import java.util.ArrayList;


/**
 * This pop up is use to remove an exit point
 * from a room, user choose a room and then
 * exit point from drop down list
 * @author Cat Hoang Huy
 */
public class RemoveExitPointPopup extends JFrame{

    private JPanel panel;
    private JComboBox exitPointList = new JComboBox();
    private JComboBox roomListCB;
    private JButton removeExitPointBT = new JButton("Remove");

    public RemoveExitPointPopup() {
        actionPerformed();
        panel = new JPanel();
        setTitle("CHOOSE AN EXIT POINT");
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select an Room:"));
        panel.add(roomListCB);
        panel.add(new JLabel("Select Exit Point:"));
        panel.add(exitPointList);
        panel.add(removeExitPointBT);

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
        exitPointList = new JComboBox(MainView.model.getRoom((RoomReference) roomListCB.getSelectedItem()).getExitList());
        roomListCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exitPointList = new JComboBox(MainView.model.getRoom((RoomReference) roomListCB.getSelectedItem()).getExitList());
                setVisible(false);
                panel.removeAll();
                panel.add(new JLabel("Select an Room:"));
                panel.add(roomListCB);
                panel.add(new JLabel("Select an Exit Point:"));
                panel.add(exitPointList);
                panel.add(removeExitPointBT);
                add(panel);
                setVisible(true);
                pack();
            }
        });
        removeExitPointBT.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (exitPointList.getSelectedItem() != null&&!(exitPointList.getSelectedItem().equals(""))) {
                    System.out.println(exitPointList.getSelectedItem());
                    MainView.model.removeExitPoint((RoomReference) roomListCB.getSelectedItem(), ((ExitPoint) exitPointList.getSelectedItem()).getName());

                } else {
                    JOptionPane.showMessageDialog(null, "THERE ARE NO EXIT POINT TO REMOVE");
                }
                dispose();
            }
        });
    }
}
