package client.gui.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ModelsMapTest extends JFrame {
    private MapPanel mapPanel;
    private List<Coordinates> coordinatesList;

    public ModelsMapTest() {
        setTitle("Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        coordinatesList = new ArrayList<>();

        mapPanel = new MapPanel();
        setContentPane(mapPanel);

        // Add coordinates to the map
        coordinatesList.add(new Coordinates(100, 100));
        coordinatesList.add(new Coordinates(200, 200));
        coordinatesList.add(new Coordinates(300, 300));

        // Register mouse listener for map movement
        mapPanel.addMouseListener(new MouseAdapter() {
            private int clickX;
            private int clickY;

            @Override
            public void mousePressed(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
                mapPanel.setClickX(clickX);
                mapPanel.setClickY(clickY);
                System.out.println(clickX);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickX = 0;
                clickY = 0;
            }
        });

        mapPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx =  mapPanel.getClickX() - e.getX();
                int dy = mapPanel.getClickY() - e.getY();
                System.out.println(dx + " " + dy);
                mapPanel.moveMap(dx, dy);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModelsMapTest map = new ModelsMapTest();
            map.setVisible(true);
        });
    }

    private class MapPanel extends JPanel {
        private int mapX;
        private int mapY;
        private int clickX;
        private int clickY;

        public void setClickX(int clickX) {
            this.clickX = clickX;
        }

        public void setClickY(int clickY) {
            this.clickY = clickY;
        }

        public MapPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(800, 600));
        }

        public void moveMap(int dx, int dy) {
            mapX += dx * 0.01;
            mapY += dy * 0.01;

            repaint();
        }

        public int getClickX() {
            return clickX;
        }

        public int getClickY() {
            return clickY;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            // Translate the graphics context to move the map
            //g2d.translate(mapX, mapY);

            // Draw the coordinates
            for (Coordinates coordinates : coordinatesList) {
                int x = (int) coordinates.getX() - mapX;
                int y = (int) coordinates.getY() - mapY;
                g2d.fillOval(x, y, 10, 10);
            }
            g2d.dispose();
            repaint();
        }
    }

}
