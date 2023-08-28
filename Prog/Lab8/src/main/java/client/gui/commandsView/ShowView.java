package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomScrollBarUI;
import client.gui.ui.Table;
import client.main.Main;
import client.сommands.Command;
import client.сommands.Show;
import common.models.Model;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowView extends CommandView{

    private JLabel answer = new JLabel("");
    private ModelTree tree;

    public ShowView(Command command, ModelTree tree) {
        super(command);
        this.tree = tree;
    }

    @Override
    public void render() {
        MainWindow mainWindow = MainWindow.getInstance();
        var cards = mainWindow.getCards();
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(0x242F3D));

        card.add(Box.createVerticalStrut(10));
        answer.setUI(new CustomLabelUI());
        card.add(answer);

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

    public void addButton(JPanel panel, String name) {
        var button = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(name));
        button.setUI(new CustomButtonUI());
        buttons.put(button, name);
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            MainWindow.getInstance().getTablePanel().deleteComparator();
            MainWindow.getInstance().getTablePanel().deletePredicate();
            commandManager.executeCommand(command.getName(), "");
        });
    }
}
