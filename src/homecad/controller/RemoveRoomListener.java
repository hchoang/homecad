/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.controller;


import homecad.view.popup.RemoveRoomPopup;
import java.awt.event.*;
import javax.swing.*;

/**
* This Listener is use to handle the action when
 * user click on the Remove Room on the menu
 * @author satthuvdh
 */
public class RemoveRoomListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JFrame frame = new RemoveRoomPopup();

        
    }
}
