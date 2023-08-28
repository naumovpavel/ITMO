package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.*;
import client.main.Main;
import client.utils.input.BuilderStrategy;
import client.сommands.Show;
import common.network.Response;
import common.network.Status;
import common.utils.Converter;
import common.utils.FilterTypes;
import common.utils.ModelTree;
import client.сommands.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FilterView extends CommandView implements ItemListener {
    private ModelTree tree;
    private BuilderStrategy builder;
    private JLabel answer = new JLabel();
    private String path;
    private JTextField value;
    private FilterTypes filterType = FilterTypes.EQUAL;
    private HashMap<String, Class> pathToType = new HashMap<>();


    public FilterView(Command command, ModelTree tree) {
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
        ResourceBundle r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        answer.setUI(new CustomLabelUI());
        card.add(answer);
        card.add(Box.createVerticalStrut(10));
        addLabel(card, "field");
        addCBTypes(card);
        addLabel(card, "value");
        addTextField(card, fields, "value");
        addLabel(card, "filterType");
        addCB(card);
        addButton(card, "filter");
        addRemoveButton(card, "removeFilters");
        addScrollPanel(cards, card, command.getName());
    }

    public void addCBTypes(JPanel card) {
        ArrayList<String> names = new ArrayList<>();
        getFieldsNames(tree, names, "");
        var cb = new JComboBox<>(names.toArray());
        setCBView(cb);
        cb.addItemListener(this);
        path = (String) cb.getSelectedItem();
        card.add(cb);
    }

    public void addCB(JPanel card) {
        var cbFilter = new JComboBox<>(FilterTypes.values());
        setCBView(cbFilter);
        cbFilter.addItemListener((var e) -> {
            filterType = (FilterTypes)e.getItem();
        });
        card.add(cbFilter);
        card.add(Box.createVerticalStrut(10));
    }

    private void getFieldsNames(ModelTree tree, ArrayList<String> names, String prefix) {
        for(var field : tree.getFields()) {
            if(!field.isPrimitive()) {
                getFieldsNames(field, names, prefix + field.getName() + ".");
                continue;
            }

            pathToType.put(prefix + field.getName(), field.getType());
            names.add(prefix + field.getName());
        }
    }

    public void addTextField(JPanel panel, Map<String, Object> fields, String name) {
        var field = new JTextField();
        field.setUI(new CustomTextFieldUI());
        fields.put(name, field);
        panel.add(Box.createVerticalStrut(10));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    private void addLabel(JPanel panel, String name) {
        var label = new JLabel(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(name));
        label.setUI(new CustomLabelUI());
        labels.put(label, name);
        panel.add(Box.createVerticalStrut(10));
        panel.add(label);
    }

    public void addButton(JPanel panel, String name) {
        var r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        var button = new JButton(r.getString(name));
        buttons.put(button, name);
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var event) -> {
            try {
                answer.setText("");
                var value = Converter.convert(pathToType.get(path), ((JTextField) fields.get("value")).getText());
                MainWindow.getInstance().getTablePanel().addPredicate(path, pathToType.get(path), value, filterType);
            } catch (IllegalArgumentException e) {
                answer.setForeground(Color.RED);
                answer.setText(e.getMessage());
            }
        });
    }

    public void addRemoveButton(JPanel panel, String name) {
        var r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        var button = new JButton(r.getString(name));
        buttons.put(button, name);
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var event) -> {
            MainWindow.getInstance().getTablePanel().deletePredicate();
        });
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        path = (String)e.getItem();
    }
}
