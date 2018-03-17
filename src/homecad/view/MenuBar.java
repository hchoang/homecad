/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view;

import homecad.controller.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  This is the menu bar of the GUI
 *  This menu will provide some basic action such as add room, remove room, add exit point etc.
 * @author Cat Hoang Huy
 */
public class MenuBar extends JMenuBar {

    public MenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        add(fileMenu);
        add(editMenu);
        JMenuItem resetAction = new JMenuItem("Reset");
        JMenuItem exitAction = new JMenuItem("Exit");

        JMenuItem addRoomAction = new JMenuItem("Add Room");
        addRoomAction.addActionListener(new AddRoomListener());

        JMenuItem addExitPointAction = new JMenuItem("Add Exit Point");
        addExitPointAction.addActionListener(new AddExitPointListener());

        JMenuItem addItemAction = new JMenuItem("Add Item");
        addItemAction.addActionListener(new AddItemListener());

        JMenuItem removeRoomAction = new JMenuItem("Remove Room");
        removeRoomAction.addActionListener(new RemoveRoomListener());

        JMenuItem removeExitPointAction = new JMenuItem("Remove Exit Point");
        removeExitPointAction.addActionListener(new RemoveExitPointListener());

        JMenuItem removeItemAction = new JMenuItem("Remove Item");
        removeItemAction.addActionListener(new RemoveItemListener());


        editMenu.add(addRoomAction);
        editMenu.add(addExitPointAction);
        editMenu.add(addItemAction);

        editMenu.add(removeRoomAction);
        editMenu.add(removeExitPointAction);
        editMenu.add(removeItemAction);

        resetAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MainView.model.reset();
            }
        });

        exitAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitAction);

        editMenu.add(resetAction);

    }
}
