package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomTextFieldUI;
import client.сommands.Command;
import common.network.Response;
import common.network.Status;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MaxByNameView extends CommandView{
    private JLabel answer;

    public MaxByNameView(Command command) {
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


}
