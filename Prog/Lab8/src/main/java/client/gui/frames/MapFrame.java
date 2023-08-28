package client.gui.frames;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Coordinates {
    private long x;
    private long y;

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}



public class MapFrame extends JTable {

    private List<Coordinates> coordinatesList;
    private BufferedImage sprite;
    private BufferedImage[] sprites = new BufferedImage[30];
    private double rotationAngle;
    private double scaleFactor;
    private int frame = 0;

    public MapFrame(List<Coordinates> coordinatesList) {
        this.coordinatesList = coordinatesList;
        try {
            for(int i = 0; i < 30; i++) {
                sprite = ImageIO.read(new File("C:\\ITMO\\Prog\\Lab8\\src\\main\\java\\client\\gui\\frames\\anim\\"+i+".gif"));
                sprites[i] = sprite;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rotationAngle = 0.0;
        scaleFactor = 1.0;
        initialize();
        startAnimation();
    }

    private void initialize() {
        setMinimumSize(new Dimension(800, 800));
        setSize(800, 600);
        setVisible(true);
    }

    private void startAnimation() {
        Timer animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Update rotation angle and scale factor
                rotationAngle += 0;
                //scaleFactor += 0.01;
                    repaint();
            }
        }, 0, 33); // 10ms delay between each animation frame
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        frame = (frame + 1)%30;
        for (Coordinates coordinates : coordinatesList) {
            int x = (int) coordinates.getX();
            int y = (int) coordinates.getY();

            // Apply transformations
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            //transform.rotate(Math.toRadians(rotationAngle), sprites[frame].getWidth() / 2.0, sprites[frame].getHeight() / 2.0);
            //transform.scale(scaleFactor, scaleFactor);

            // Draw the transformed sprite
            g2d.drawImage(sprites[frame], transform, null);
        }
        Toolkit.getDefaultToolkit().sync();
        //g2d.dispose();
    }

    public static void main(String[] args) {

        JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.setVisible(true);

        List<Coordinates> coordinatesList = List.of(
                new Coordinates(100, 100),
                new Coordinates(200, 300),
                new Coordinates(200, 300),
                new Coordinates(200, 300),
                new Coordinates(200, 300),
                new Coordinates(200, 300),
                new Coordinates(400, 200)
        );
        frame1.add(new MapFrame(coordinatesList));
        frame1.setSize(800,800);

        //SwingUtilities.invokeLater(() -> new MapFrame(coordinatesList));
    }
}


