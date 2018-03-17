/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.controller;

import homecad.view.popup.AddItemPopup;
import java.awt.event.*;
import javax.swing.*;

/**
 * This Listener is use to handle the action when
 * user click on the Add Item on the menu
 *
 * @author Cat Hoang Huy
 */
public class AddItemListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JFrame frame = new AddItemPopup();
    }
}
