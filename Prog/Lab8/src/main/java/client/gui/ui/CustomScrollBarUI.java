package client.gui.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private Color thumbColor = new Color(0x3E546A);
    private Color trackColor = new Color(0x18222B);
    private Color thumbRolloverColor = new Color(0x3E546A);
    private Color thumbPressedColor = new Color(0x3E546A);

    @Override
    protected void configureScrollBarColors() {
        // Set the color for the thumb (scrollbar handle)
        super.thumbColor = thumbColor;

        // Set the color for the track (scrollbar background)
        super.trackColor = trackColor;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        boolean isRollover = isThumbRollover();
        boolean isPressed = isDragging;

        if (isPressed) {
            g2d.setColor(thumbPressedColor);
        } else if (isRollover) {
            g2d.setColor(thumbRolloverColor);
        } else {
            g2d.setColor(thumbColor);
        }

        g2d.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
        g2d.dispose();
    }
}
