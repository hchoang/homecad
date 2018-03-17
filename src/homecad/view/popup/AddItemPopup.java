/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view.popup;

import homecad.model.*;
import homecad.model.exception.*;
import homecad.view.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This frame will pop up when user click on the Menu
 * Edit > Add Item
 * @author Cat Hoang Huy
 */
public class AddItemPopup extends JFrame {

    private JComboBox listRoomComboBox;
    private String[] itemComboBox = {"Table", "Chair", "TV", "Fan", "Computer"};
    private JComboBox itemList = new JComboBox(itemComboBox);
    private JTextField price = new JTextField(5);
    private JTextField itemName = new JTextField(15);

    public AddItemPopup() {
        if (MainView.model.getAllRooms() == null) {
            listRoomComboBox = new JComboBox();
        } else {
            Room[] room = MainView.model.getAllRooms();
            ArrayList<RoomReference> roomLocation = new ArrayList<RoomReference>();
            for (int i = 0; i < room.length; i++) {
                roomLocation.add(room[i].getLocation());
            }
            listRoomComboBox = new JComboBox(roomLocation.toArray());
        }
        JButton addItemBT = new JButton("Add");
        setTitle("ADD AN ITEM");
        setLayout(new FlowLayout());
        add(new JLabel("Add an "));
        add(itemList);
        add(new JLabel(" price :"));
        add(price);
        add(new JLabel(" to :"));
        add(listRoomComboBox);
        add(new JLabel(" item name :"));
        add(itemName);
        add(addItemBT);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();

        addItemBT.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (listRoomComboBox.getSelectedIndex() < 2) {
                        if (price.getText().equals("") || price.getText() == null) {
                            MainView.model.addItem(((RoomReference) listRoomComboBox.getSelectedItem()), new HouseholdItem(((String)itemList.getSelectedItem())+" "+itemName.getText()));
                        } else {
                            MainView.model.addItem(((RoomReference) listRoomComboBox.getSelectedItem()), new HouseholdItem(((String)itemList.getSelectedItem())+" "+itemName.getText(), Integer.parseInt(price.getText())));
                        }
                    } else {
                        if (price.getText().equals("") || price.getText() == null) {
                            MainView.model.addItem(((RoomReference) listRoomComboBox.getSelectedItem()), new ElectronicAppliance(((String)itemList.getSelectedItem())+" "+itemName.getText()));
                        } else {
                            MainView.model.addItem(((RoomReference) listRoomComboBox.getSelectedItem()), new ElectronicAppliance(((String)itemList.getSelectedItem())+" "+itemName.getText(), Integer.parseInt(price.getText())));
                        }
                    }


                } catch (HomeCADException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (AddtoRoomException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "PRICE MUST BE A NUMBER");
                }
                    finally {
                    dispose();
                }
            }
        });
    }
}
