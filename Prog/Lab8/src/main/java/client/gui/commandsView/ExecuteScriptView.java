package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomScrollBarUI;
import client.gui.ui.CustomTextFieldUI;
import client.main.Main;
import client.сommands.Command;
import common.network.Response;
import common.network.Status;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;

public class ExecuteScriptView extends CommandView{

    private JLabel answer;

    public ExecuteScriptView(Command command) {
        super(command);
    }

    @Override
    public void render() {
        MainWindow mainWindow = MainWindow.getInstance();
        var cards = mainWindow.getCards();
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(0x242F3D));

        card.add(Box.createVerticalStrut(10));
        answer = new JLabel("");
        answer.setUI(new CustomLabelUI());
        card.add(answer);

        card.add(Box.createVerticalStrut(10));
        var label = new JLabel(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("filePath"));
        labels.put(label, "filePath");
        label.setUI(new CustomLabelUI());
        card.add(label);

        card.add(Box.createVerticalStrut(10));
        addTextField(card, fields, "filePath");

        addButton(card, command.getName());

        addScrollPanel(cards, card, command.getName());
    }

    @Override
    public void renderResponse(Response response) {
        if(response.getStatus() != Status.OK) {
            answer.setForeground(Color.RED);
        } else {
            answer.setForeground(new Color(0xE7ECF1));
        }
        answer.setText(splitString(response.getAnswer()));
    }

    public void addTextField(JPanel panel, Map<String, Object> fields, String name) {
        var field = new JTextField();
        field.setUI(new CustomTextFieldUI());
        fields.put(name, field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    public void addButton(JPanel panel, String name) {
        var button = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(name));
        button.setUI(new CustomButtonUI());
        buttons.put(button, name);
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            commandManager.executeCommand(command.getName(),  ((JTextField)fields.get("filePath")).getText());
        });
    }
}
