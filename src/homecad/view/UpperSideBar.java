/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view;

import homecad.model.Storey;
import homecad.model.facade.HomeCADEngine;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *  This panel is located on the South border of the main
 * frame. this panel display the the basic information of
 * the owner and home 
 * @author Cat Hoang Huy
 */
public class UpperSideBar extends JPanel implements Observer {

    private JComboBox cb;
    private JTextArea houseNameTextArea = new JTextArea(MainView.model.getOwner().getName());
    private JTextArea availableBudgetTextArea = new JTextArea(String.valueOf(MainView.model.getOwner().getAvailableBudget()));
    private JTextArea houseSizeTextArea = new JTextArea(String.valueOf(MainView.model.calculateHouseSize()));
    private JTextArea houseCostTextArea = new JTextArea(String.valueOf(MainView.model.calculateHouseCost()));

    public UpperSideBar() {
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        setLayout(new GridLayout(2, 6));

        houseNameTextArea.setBackground(new Color(238, 238, 238));
        houseNameTextArea.setBorder(BorderFactory.createEtchedBorder());
        houseNameTextArea.setEditable(false);

        availableBudgetTextArea.setBackground(new Color(238, 238, 238));
        availableBudgetTextArea.setBorder(BorderFactory.createEtchedBorder());
        availableBudgetTextArea.setEditable(false);

        houseSizeTextArea.setBackground(new Color(238, 238, 238));
        houseSizeTextArea.setBorder(BorderFactory.createEtchedBorder());
        houseSizeTextArea.setEditable(false);

        houseCostTextArea.setBackground(new Color(238, 238, 238));
        houseCostTextArea.setBorder(BorderFactory.createEtchedBorder());
        houseCostTextArea.setEditable(false);

        cb = new JComboBox(MainView.model.getStoreys());
        cb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MainView.model.setCurrentStorey(((Storey) cb.getSelectedItem()).getStoryNumber());
            }   
        });
        add(new JLabel("Owner's Name:"));
        add(houseNameTextArea);
        add(new JLabel("Available Budget:"));
        add(availableBudgetTextArea);

        add(new JLabel("House's Total Size:"));
        add(houseSizeTextArea);
        add(new JLabel("Choose House's storey:"));
        add(cb);
        add(new JPanel());
        add(new JPanel());
        add(new JLabel("Total House Cost"));
        add(houseCostTextArea);

    }

    public void update(Observable o, Object arg) {
        cb.setModel(new DefaultComboBoxModel(MainView.model.getStoreys()));

        availableBudgetTextArea.setText(String.valueOf(((HomeCADEngine) o).getOwner().getAvailableBudget()));
        houseSizeTextArea.setText(String.valueOf(((HomeCADEngine) o).calculateHouseSize()));
        houseCostTextArea.setText(String.valueOf(((HomeCADEngine) o).calculateHouseCost()));

    }
}
