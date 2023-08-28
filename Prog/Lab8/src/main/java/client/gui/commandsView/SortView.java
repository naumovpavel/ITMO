package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomScrollBarUI;
import client.gui.ui.Table;
import client.main.Main;
import client.сommands.Show;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;
import client.сommands.Command;
import common.utils.SortTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SortView extends CommandView implements ItemListener {
    private ModelTree tree;
    private JLabel answer;
    private String path;
    private SortTypes sortType = SortTypes.GREATER;
    private HashMap<String, Class> pathToType = new HashMap<>();


    public SortView(Command command, ModelTree tree) {
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

        addLabel(card, "field");

        //JComboBox
        ArrayList<String> names = new ArrayList<>();
        getFieldsNames(tree, names, "");
        var cb = new JComboBox<>(names.toArray());
        setCBView(cb);
        cb.addItemListener(this);
        card.add(cb);

        addLabel(card, "sortType");

        var cbFilter = new JComboBox<>(SortTypes.values());
        setCBView(cbFilter);
        cbFilter.addItemListener((var e) -> {
            sortType = (SortTypes) e.getItem();
        });
        card.add(cbFilter);
        card.add(Box.createVerticalStrut(10));

        addButton(card, "sort");
        addRemoveButton(card, "remove_sort");
        addScrollPanel(cards, card, command.getName());
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

    private void addLabel(JPanel panel, String name) {
        var label = new JLabel(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(name));
        labels.put(label, name);
        label.setUI(new CustomLabelUI());
        panel.add(Box.createVerticalStrut(10));
        panel.add(label);
    }

    public void addButton(JPanel panel, String name) {
        var button = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(name));
        button.setUI(new CustomButtonUI());
        buttons.put(button, name);
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            System.out.println(11);
            MainWindow.getInstance().getTablePanel().addComparator(path, pathToType.get(path), sortType);
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

    public void addRemoveButton(JPanel panel, String name) {
        var r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        var button = new JButton(r.getString(name));
        buttons.put(button, name);
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var event) -> {
            System.out.println(1);
            MainWindow.getInstance().getTablePanel().deleteComparator();
        });
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        path = (String)e.getItem();
    }
}
