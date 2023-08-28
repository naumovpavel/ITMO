package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomScrollBarUI;
import client.main.Main;
import client.сommands.CommandManager;
import common.network.Response;
import client.сommands.Command;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class CommandView {
    protected Command command;
    protected Map<String, Object> fields = new HashMap<>();
    protected Map<String, Object> enums = new HashMap<>();
    protected static CommandManager commandManager;
    protected HashMap<JButton, String> buttons = new HashMap<>();
    protected HashMap<JLabel, String> labels = new HashMap<>();


    public CommandView(Command command) {
        this.command = command;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void render() {
        MainWindow mainWindow = MainWindow.getInstance();
        var cards = mainWindow.getCards();
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(0x242F3D));
        cards.add(card, command.getName());
    }

    protected void setCBView(JComboBox<?> cb) {
        cb.setFont(new Font("Arial", Font.PLAIN, 14));
        cb.setForeground(new Color(0xE7ECF1));
        cb.setBackground(new Color(0x3E546A));
        cb.setMaximumRowCount(5);
        cb.setMaximumSize(new Dimension(100, 30));
        cb.setPreferredSize(new Dimension(100, 30));
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        cb.setEditable(false);
        cb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    protected  void addScrollPanel(JPanel cards, JPanel card, String name) {
        JScrollPane scrollPane = new JScrollPane(card);
        scrollPane.setBackground(new Color(0x242F3D));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        cards.add(wrapperPanel, name);
    }

    public void addButton(JPanel panel, String name) {
        var button = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(name));
        button.setUI(new CustomButtonUI());
        panel.add(button);
        buttons.put(button, name);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            commandManager.executeCommand(command.getName(), "");
        });
    }
    public abstract void renderResponse(Response response);

    public static void setCommandManager(CommandManager commandManager) {
        CommandView.commandManager = commandManager;
    }

    public void changeLocale() {
        var r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        for(var x : buttons.keySet()) {
            x.setText(r.getString(buttons.get(x)));
        }
        for(var x : labels.keySet()) {
            x.setText(r.getString(labels.get(x)));
        }
    }

    protected String splitString(String s) {
        StringBuilder res = new StringBuilder("<html> <div style='text-align: center; width = 100%;'>");
        int chunkSize = 35;
        int length = s.length();
        for (int i = 0; i < length; i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, length);
            String chunk = s.substring(i, endIndex);
            res.append(chunk).append("<br\\>");
        }
        res.append("</div></html>");
        return res.toString();
    }
}
