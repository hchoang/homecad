/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.controller;

import homecad.view.popup.AddRoomPopup;
import java.awt.event.*;
import javax.swing.*;

/**
* This Listener is use to handle the action when
 * user click on the Add Room on the menu
 * @author satthuvdh
 */
public class AddRoomListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JFrame frame = new AddRoomPopup();
    }
}
