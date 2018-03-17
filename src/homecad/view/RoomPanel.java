/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homecad.view;

import homecad.model.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This panel will display all detail in one specific room
 * including exit point, items
 * @author Cat Hoang Huy
 */
public class RoomPanel extends JPanel {

    private int x, y, z;

    public RoomPanel(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }
    /**
     * this method use the paintItem method to paint all
     * of the room's item and also draw all of the exit point
     * that the room has
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Room room = MainView.model.getRoom(new RoomReference(x, y, z));
        if (room != null) {
            g.setColor(new Color(95, 207, 244));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(new Color(247,201,69));
            if (room.getExitList() != null || room.getExitList().length != 0) {
                ExitPoint[] exitPoint = room.getExitList();
                for (int i = 0; i < exitPoint.length; i++) {
                    if (exitPoint[i].getDestination().getY() > room.getLocation().getY()) {
                        g.fillRect(this.getWidth() - 10, 10, 10, this.getHeight() - 20);
                    }
                    if (exitPoint[i].getDestination().getY() < room.getLocation().getY()) {
                        g.fillRect(0, 10, 10, this.getHeight() - 20);
                    }
                    if (exitPoint[i].getDestination().getX() > room.getLocation().getX()) {
                        g.fillRect(30, this.getHeight() - 10, this.getWidth() - 60, 10);
                    }
                    if (exitPoint[i].getDestination().getX() < room.getLocation().getX()) {
                        g.fillRect(30, 0, this.getWidth() - 60, 10);
                    }
                }
            }
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, this.getWidth(), this.getHeight());
            Item[] itemList = room.getItemList();

            int yTemp = 10;
            int xTemp = 10;
            for (int i = 0; i < itemList.length; i++) {
                if (i == 0 || i == 3 || i == 6) {
                    xTemp = 10;
                }
                if (i < 3) {
                    yTemp = 10;
                    paintItem(itemList[i], xTemp, yTemp, g);
                    xTemp += 48;
                } else if (i < 6) {
                    yTemp = 34;
                    paintItem(itemList[i], xTemp, yTemp, g);
                    xTemp += 48;
                } else {
                    yTemp = 58;
                    paintItem(itemList[i], xTemp, yTemp, g);
                    xTemp += 48;
                }
            }
        } else {
            g.setColor(new Color(219, 231, 239));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
    /**
     * this method will paint all of the item in the room to the panel
     * @param item
     * @param x
     * @param y
     * @param g
     */
    private void paintItem(Item item, int x, int y, Graphics g) {
        BufferedImage image = null;
        if (item.getName().startsWith("Table")) {

            try {
                URL imageURL = getClass().getResource("table.jpg");
                image = ImageIO.read(imageURL);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "IMAGE NOT FOUND", JOptionPane.ERROR_MESSAGE);
            }
            g.drawImage(image, x, y, 48, 24, null);
        }
        if (item.getName().startsWith("Chair")) {

            try {
                URL imageURL = getClass().getResource("chair.jpg");
                image = ImageIO.read(imageURL);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "IMAGE NOT FOUND", JOptionPane.ERROR_MESSAGE);
            }
            g.drawImage(image, x, y, 48, 24, null);
        }
        if (item.getName().startsWith("TV")) {

            try {
                URL imageURL = getClass().getResource("tv.jpg");
                image = ImageIO.read(imageURL);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "IMAGE NOT FOUND", JOptionPane.ERROR_MESSAGE);
            }
            g.drawImage(image, x, y, 48, 24, null);
        }
        if (item.getName().startsWith("Computer")) {

            try {
                URL imageURL = getClass().getResource("computer.jpg");
                image = ImageIO.read(imageURL);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "IMAGE NOT FOUND", JOptionPane.ERROR_MESSAGE);
            }
            g.drawImage(image, x, y, 48, 24, null);
        }
        if (item.getName().startsWith("Fan")) {

            try {
                URL imageURL = getClass().getResource("fan.jpg");
                image = ImageIO.read(imageURL);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "IMAGE NOT FOUND", JOptionPane.ERROR_MESSAGE);
            }
            g.drawImage(image, x, y, 48, 24, null);
        }
    }
}
