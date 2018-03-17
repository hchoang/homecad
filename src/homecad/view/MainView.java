/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view;

import homecad.model.facade.*;
import java.awt.BorderLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 *  This is the main view of the program
 *  Basically, this is a frame and it holds  most of the graphics components
 *  that user interacts with
 * @author satthuvdh
 */
public class MainView extends JFrame {

    public static HomeCADEngine model = new HomeCADEngine();
    private static RoomDisplay panel;
    private static UpperSideBar upBar;
    private MenuBar menuBar;
    private static RightSideBar rightBar;

    public MainView() {
        String name;
        /**
         * This section get the input from the user include owner's name and budget
         * it also check the input is valid or not
         */
        do {
            name = JOptionPane.showInputDialog("Enter Owner's Name:");
            if (name == null || name.equals("")) {
                JOptionPane.showMessageDialog(null, "NAME ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } while (name == null || name.equals(""));

        String budget;
        Matcher matcher;
        do {
            budget = JOptionPane.showInputDialog("Enter Owner's Budget");
            Pattern pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(budget);
            if (!matcher.matches() || budget.toCharArray().length > 10) {
                JOptionPane.showMessageDialog(null, "BUDGET ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } while (!matcher.matches() || budget.toCharArray().length > 10);
        /**
         * This part create the frame and display to the screen
         */
        model = new HomeCADEngine(name, Integer.parseInt(budget));

        setSize(1366, 730);
        upBar = new UpperSideBar();
        panel = new RoomDisplay();
        menuBar = new MenuBar();
        rightBar = new RightSideBar();
        setJMenuBar(menuBar);
        add(panel);
        add(upBar, BorderLayout.NORTH);
        add(rightBar, BorderLayout.EAST);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        MainView frame = new MainView();
        model.addObserver(panel);
        model.addObserver(upBar);
        model.addObserver(rightBar);
    }
}
