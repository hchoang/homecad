/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view.popup;

import homecad.model.*;
import homecad.model.exception.*;
import homecad.view.MainView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * This frame is used for the user to add room to the house
 * this frame user enter the location and the name of the storey
 * @author Cat Hoang Huy
 */
public class AddRoomPopup extends JFrame {

    private JTextField x = new JTextField(3);
    private JTextField y = new JTextField(3);
    private JTextField z = new JTextField(3);
    private JTextField name = new JTextField(20);

    public AddRoomPopup() {
        JButton add = new JButton("Add Room");

        setTitle("ADD ROOM");
        setVisible(true);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        add(add);
        add(new JLabel("x:"));
        add(x);
        add(new JLabel("y:"));
        add(y);
        add(new JLabel("z:"));
        add(z);
        add(new JLabel("Name:"));
        add(name);
        pack();
        add.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcherX = pattern.matcher(x.getText());
                Matcher matcherY = pattern.matcher(y.getText());
                Matcher matcherZ = pattern.matcher(z.getText());
                if (x.getText().equals("") || y.getText().equals("") || z.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter 3 dimension to add room");
                } else if (!matcherX.matches() || !matcherY.matches() || !matcherZ.matches()) {
                    JOptionPane.showMessageDialog(null, "Location must be number");
                } else if (Integer.parseInt(x.getText()) > 7 || Integer.parseInt(y.getText()) > 7) {
                    JOptionPane.showMessageDialog(null, "x,y must be lower or equal 7 ");
                } else {
                    RoomReference ref = new RoomReference(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), Integer.parseInt(z.getText()));

                    if (name != null) {
                        try {
                            MainView.model.addRoom(new Room(ref, name.getText()));

                        } catch (HomeCADException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
                dispose();
            }
        });
    }
}
