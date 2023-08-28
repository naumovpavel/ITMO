package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomScrollBarUI;
import client.gui.ui.CustomTextFieldUI;
import client.main.Main;
import client.utils.input.ArrayReader;
import client.utils.input.BuilderStrategy;
import client.сommands.ManagerMode;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;
import client.сommands.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddView extends CommandView{
    private ModelTree tree;
    private BuilderStrategy builder;
    protected JLabel answer;


    public AddView(Command command, ModelTree tree, BuilderStrategy builder) {
        super(command);
        this.tree = tree;
        this.builder = builder;
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
        fillCard(card, tree, "", fields, enums);
        card.add(Box.createVerticalStrut(10));
        addButton(card, ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(command.getName()));

        addScrollPanel(cards, card, command.getName());
    }

    public void fillCard(JPanel card, ModelTree tree, String pref, Map<String, Object> fields, Map<String, Object> enums) {
        for(var field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                continue;
            }

            if(!field.isPrimitive()) {
                fields.put(field.getName(), new HashMap<String, Object>());
                enums.put(field.getName(), new HashMap<String, Object>());
                fillCard(card, field, pref + field.getName() + ".", (Map<String, Object>) fields.get(field.getName()), (Map<String, Object>) enums.get(field.getName()));
                continue;
            }

            addLabel(card, pref + field.getName());
            if(field.isEnum()) {
                addEnum(card, field, fields, enums);
                continue;
            }

            addTextField(card, fields, field.getName());
        }
    }

    private void addEnum(JPanel panel, ModelTree field, Map<String, Object> fields, Map<String, Object> enums) {
        var cb = new JComboBox<>(field.getEnumConstants().keySet().toArray(new String[0]));
        setCBView(cb);
        panel.add(cb);
        var textField = new JTextField();
        cb.addItemListener((var e) -> {
            textField.setText((String) e.getItem());
        });
        cb.setSelectedIndex(0);
        fields.put(field.getName(), textField);
        enums.put(field.getName(), cb);
        panel.add(Box.createVerticalStrut(10));
    }

    public void addTextField(JPanel panel, Map<String, Object> fields, String name) {
        var field = new JTextField();
        field.setUI(new CustomTextFieldUI());
        fields.put(name, field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    public void addButton(JPanel panel, String name) {
        var button = new JButton(name);
        buttons.put(button, command.getName());
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            ArrayList<String> values = new ArrayList<>();
            readFields(tree, fields, enums, values);
            ArrayReader reader = new ArrayReader();
            reader.setStream(values.toArray(String[]::new));
            builder.setReader(reader, ManagerMode.GUI);
            commandManager.executeCommand(command.getName(), "");
        });
    }

    private void readFields(ModelTree tree, Map<String, Object> fields, Map<String, Object> enums, ArrayList<String> array) {
        for(var field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                continue;
            }
            if(!field.isPrimitive()) {
                readFields(field, (Map<String, Object>) fields.get(field.getName()), (Map<String, Object>) enums.get(field.getName()), array);
                continue;
            }
            if(field.isEnum())
                array.add(((JComboBox<String>)enums.get(field.getName())).getSelectedItem().toString());
            else
                array.add(((JTextField)fields.get(field.getName())).getText());
        }
    }

    private void addLabel(JPanel panel, String name) {
        var label = new JLabel(name);
        label.setUI(new CustomLabelUI());
        panel.add(label);
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
