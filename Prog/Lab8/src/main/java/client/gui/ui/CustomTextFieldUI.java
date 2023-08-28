package client.gui.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class CustomTextFieldUI extends BasicTextFieldUI {
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private Color foregroundColor = new Color(0xE7ECF1);
    private Color backgroundColor = new Color(0x3E546A);
    private Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private Dimension maximumSize = new Dimension(250, 30);
    private float alignmentX = Component.CENTER_ALIGNMENT;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        JTextComponent textField = getComponent();
        textField.setFont(font);
        textField.setForeground(foregroundColor);
        textField.setBackground(backgroundColor);
        textField.setBorder(border);
        textField.setMaximumSize(maximumSize);
        textField.setAlignmentX(alignmentX);
    }
}
