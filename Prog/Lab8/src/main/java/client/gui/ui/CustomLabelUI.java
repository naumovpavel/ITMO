package client.gui.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

public class CustomLabelUI extends BasicLabelUI {
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private Color foregroundColor = new Color(0xE7ECF1);
    private Border border = BorderFactory.createEmptyBorder(0, 0, 5, 0);
    private float alignmentX = Component.CENTER_ALIGNMENT;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JLabel label = (JLabel) c;
        label.setFont(font);
        label.setForeground(foregroundColor);
        label.setBorder(border);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(alignmentX);
    }
}
