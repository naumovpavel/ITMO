package client.gui.ui;

import client.gui.commandsView.ChangeView;
import client.gui.frames.MainWindow;
import client.handlers.CollectionSubscriber;
import client.сommands.CommandManager;
import common.models.Coordinates;
import common.models.Model;
import common.utils.Event;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import server.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class ModelsMap extends JPanel implements CollectionSubscriber {
    private BufferedImage sprite;
    private CommandManager manager;
    private BufferedImage[] sprites = new BufferedImage[30];
    private Collection<Model> collection = new ArrayList<>();
    private HashMap<Integer, MutablePair<Integer, Event>> modelFrame = new HashMap<>();
    private ArrayList<MutablePair<Integer, Event>> tm = new ArrayList<>();
    private static String cfg;
    private int maxFrames = 30 * 3;
    private int framesCnt = 30;
    private int minFrame = maxFrames;
    private int mapX;
    private int mapY;
    private int clickX;
    private int clickY;
    private Integer tmp = 0;
    private String anim;
    private boolean needRepaint = false;

    public ModelsMap(CommandManager manager) {
        this.manager = manager;
        Properties info = new Properties();
        try {
            info.load(new FileInputStream(cfg));
        } catch (IOException e) {
            Main.logger.warn("Не найден конфигурационный файл");
        }
        anim = info.getProperty("anim");
        try {
            for(int i = 0; i < 30; i++) {
                    sprite = ImageIO.read(new File(anim+i+".gif"));
                sprites[i] = sprite;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
        startAnimation();
    }

    public static void setCfg(String cfg) {
        ModelsMap.cfg = cfg;
    }

    private void initialize() {
        setBackground(new Color(0xE7ECF1));

        addMouseListener(new MouseAdapter() {
            private int clickX;
            private int clickY;

            @Override
            public void mousePressed(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
                setClickX(clickX);
                setClickY(clickY);
                for(var model : collection) {
                    Coordinates coordinates = (Coordinates) model.getValues().get("coordinates");
                    int x = (int)(clickX - sprites[0].getWidth()*0.25/2);
                    int y = (int)(clickY - sprites[0].getHeight()*0.25/2);
                    if(Math.abs(x - (coordinates.getX() - mapX)) < sprites[0].getWidth() * 0.25 && Math.abs(y - (coordinates.getY() - mapY)) < sprites[0].getHeight() * 0.25) {
                        manager.executeCommand("get_by_id", String.valueOf(model.getId()));
                        var cards = MainWindow.getInstance().getCards();
                        ((CardLayout)cards.getLayout()).show(cards, "change");
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickX = 0;
                clickY = 0;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = getClickX() - e.getX();
                int dy = getClickY() - e.getY();
                moveMap(dx, dy);
            }
        });
    }

    private void startAnimation() {
        java.util.Timer animationTimer = new Timer();
        new Thread(() -> {
            animationTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (isVisible() && (minFrame < maxFrames || needRepaint)) {
                        needRepaint = false;
                        repaint();
                    }
                }
            }, 0, 33);
        }).start();// 10ms delay between each animation frame
    }

    @Override
    public synchronized void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        minFrame = maxFrames;
        Set<Integer> toDelete = new HashSet<>();
        for (var model : collection) {
            Coordinates coordinates = (Coordinates) model.getValues().get("coordinates");
            int x = (int) coordinates.getX() - mapX;
            int y = (int) coordinates.getY() - mapY;
            int frame = maxFrames;
            if(modelFrame.containsKey(model.getId())) {
                var vector = modelFrame.get(model.getId());
                frame = vector.getLeft();
                if(frame + 1 < maxFrames) {
                    vector.left = frame + 1;
                } else {
                    if(vector.getRight() == Event.DELETE) {
                        toDelete.add(model.getId());
                    }
                    modelFrame.remove(model.getId());
                }
            }
            minFrame = Math.min(minFrame, frame);
            // Apply transformations
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            //transform.rotate(Math.toRadians(rotationAngle), sprites[frame].getWidth() / 2.0, sprites[frame].getHeight() / 2.0);
            transform.scale(0.25, 0.25);


            // Draw the transformed sprite
            g2d.drawImage(sprites[frame % framesCnt], transform, null);
        }
        collection.removeIf(x -> toDelete.contains(x.getId()));
    }

    @Override
    public synchronized void update(List<Pair<Model, Event>> events) {
        for(var event : events) {
            modelFrame.put(event.getLeft().getId(), new MutablePair<>(0, event.getRight()));
            switch (event.getRight()) {
                case ADD -> collection.add(event.getLeft());
                case UPDATE -> {
                    collection.removeIf(x -> x.getId() == event.getLeft().getId());
                    collection.add(event.getLeft());
                }
            }
        }
        if(events.size() > 0) {
            needRepaint = true;
        }
    }

    @Override
    public synchronized void setCollection(Collection<Model> collection) {
        this.collection = new ArrayList<>();
        this.collection.addAll(collection);
        needRepaint = true;
    }

    public void setClickX(int clickX) {
        this.clickX = clickX;
    }

    public void setClickY(int clickY) {
        this.clickY = clickY;
    }

    public void moveMap(int dx, int dy) {
        mapX += dx * 0.01;
        mapY += dy * 0.01;
        needRepaint = true;
    }

    public int getClickX() {
        return clickX;
    }

    public int getClickY() {
        return clickY;
    }
}
