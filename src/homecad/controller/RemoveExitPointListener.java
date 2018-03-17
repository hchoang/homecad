/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.controller;


import homecad.view.popup.RemoveExitPointPopup;

import java.awt.event.*;
import javax.swing.*;

/**
* This Listener is use to handle the action when
 * user click on the Remove Exit Point on the menu
 * @author Cat Hoang Huy
 */
public class RemoveExitPointListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JFrame frame = new RemoveExitPointPopup();
    }
}
