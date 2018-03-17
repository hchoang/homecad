/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view;

import homecad.model.facade.HomeCADEngine;
import java.awt.Color;
import java.util.*;
import javax.swing.*;

/**
 * This panel will display the basic information of an room when user click on the room panel
 * @author Cat Hoang Huy
 */
public class RightSideBar extends JPanel implements Observer {

    private JTextField roomName = new JTextField(10);
    private JTextField roomLocation = new JTextField(10);
    private JTextField roomSize = new JTextField(10);
    private JTextField roomPrice = new JTextField(10);
    private JTextField storeyNumber = new JTextField(10);
    

    public RightSideBar() {

        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(242, 249, 252));


        this.add(new JLabel("Room Infomation:"));
        JPanel roomNamePanel = new JPanel();
        roomNamePanel.add(new JLabel("Room Name:"));
        roomName.setEditable(false);
        roomNamePanel.add(roomName);
        JPanel roomLocationPanel = new JPanel();
        roomLocationPanel.add(new JLabel("Room Location:"));
        roomLocation.setEditable(false);
        roomLocationPanel.add(roomLocation);
        JPanel roomSizePanel = new JPanel();
        roomSizePanel.add(new JLabel("Room Size:"));
        roomSize.setEditable(false);
        roomSizePanel.add(roomSize);
        JPanel roomPricePanel = new JPanel();
        roomPricePanel.add(new JLabel("Room Price:"));
        roomPrice.setEditable(false);
        roomPricePanel.add(roomPrice);
        
        JPanel storeyNumberPanel = new JPanel();
        storeyNumberPanel.add(new JLabel("Storey Number:"));
        storeyNumber.setEditable(false);
        storeyNumberPanel.add(storeyNumber);

        add(storeyNumberPanel);
        add(roomNamePanel);
        add(roomLocationPanel);
        add(roomSizePanel);
        add(roomPricePanel);
        for (int i = 0; i < 25; i++) {
            add(new JPanel());
        }

    }
    /**
     * this method will update all the text field in the panel
     * when user interacts with the program
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        if (((HomeCADEngine) o).getCurrentRoom() != null && ((HomeCADEngine) o).getCurrentRoom().getLocation() != null) {
            roomName.setText(((HomeCADEngine) o).getCurrentRoom().getName());
            roomLocation.setText(((HomeCADEngine) o).getCurrentRoom().getLocation().toString());
            roomSize.setText(String.valueOf(((HomeCADEngine) o).getCurrentRoom().calculateSize()));
            roomPrice.setText(String.valueOf(((HomeCADEngine) o).getCurrentRoom().calculateCost()));
            storeyNumber.setText(String.valueOf(((HomeCADEngine) o).getCurrentStorey()));
            
        } else {
            roomName.setText("");
            roomLocation.setText("");
            roomSize.setText("");
            roomPrice.setText("");
        }
    }
}
