package client.gui.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButtonUI extends BasicButtonUI {
    private Font font = new Font("Arial", Font.BOLD, 14);
    private Color foregroundColor = new Color(0xE7ECF1);
    private Color backgroundColor = new Color(0x2B5278);
    private Border border = BorderFactory.createEmptyBorder(10, 30, 10, 30);
    private float alignmentX = Component.CENTER_ALIGNMENT;
    private Color pressedBackgroundColor = new Color(0x3D6A96);

    @Override
    protected void installDefaults(AbstractButton button) {
        super.installDefaults(button);
        button.setFont(font);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
        button.setBorder(border);
        button.setAlignmentX(alignmentX);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedBackgroundColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    }
}
