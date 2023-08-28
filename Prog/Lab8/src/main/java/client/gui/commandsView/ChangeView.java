package client.gui.commandsView;

import client.gui.frames.MainWindow;
import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomTextFieldUI;
import client.main.Main;
import client.utils.input.ArrayReader;
import client.utils.input.BuilderStrategy;
import client.сommands.ManagerMode;
import common.models.Model;
import common.network.Response;
import common.network.Status;
import common.utils.Converter;
import common.utils.ModelTree;
import client.сommands.Command;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ChangeView extends CommandView{
    private ModelTree tree;
    private BuilderStrategy builder;
    private JLabel answer;
    private JTextField id;
    private ArrayList<JTextField> fieldsNames = new ArrayList<>();
    private static ChangeView instance = null;
    private JPanel changeCard;
    private JPanel changeCards;
    private int modelId = 0;


    public ChangeView(Command command, ModelTree tree, BuilderStrategy builder) {
        super(command);
        this.tree = tree;
        this.builder = builder;
        instance = this;
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

        changeCards = new JPanel(new CardLayout());
        card.add(changeCards);
        setElementCard(changeCards);
        setGetCard(changeCards);
        addScrollPanel(cards, card, command.getName());
        ((CardLayout)changeCards.getLayout()).show(changeCards, "get");
    }

    private void setGetCard(JPanel mainCard) {
        var card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(0x242F3D));
        card.add(Box.createVerticalStrut(10));
        addIdLabel(card);
        card.add(Box.createVerticalStrut(10));
        addIdTextField(card);
        addGetButton(card, "get_by_id");
        mainCard.add(card, "get");
    }

    private void addIdLabel(JPanel card) {
        var id = new JLabel("id");
        id.setUI(new CustomLabelUI());
        card.add(id);
        card.add(Box.createVerticalStrut(10));
    }

    private void addGetButton(JPanel card, String name) {
        var button = new JButton("change");
        buttons.put(button, "change");
        button.setUI(new CustomButtonUI());
        card.add(button);
        card.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            commandManager.executeCommand("get_by_id", id.getText());
        });
    }

    private void addIdTextField(JPanel card) {
        id = new JTextField();
        id.setUI(new CustomTextFieldUI());
        card.add(id);
        card.add(Box.createVerticalStrut(10));
    }

    private void setElementCard(JPanel mainCard) {
        var card = new JPanel();
        changeCard = card;
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(0x242F3D));
        card.add(Box.createVerticalStrut(10));
        fillCard(card, tree, "", fields, enums);
        card.add(Box.createVerticalStrut(10));
        addUpdateButton(card, ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("update"));
        addDeleteButton(card, ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("remove"));
        addFindButton(card, ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("find_to_change"));
        JTable dataTable = MainWindow.getInstance().getDataTable();
        mainCard.add(card, "change");
        addElementListener(dataTable, card);
    }

    private void addFindButton(JPanel panel, String name) {
        var button = new JButton(name);
        buttons.put(button, "find_to_change");
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            ((CardLayout)changeCards.getLayout()).show(changeCards, "get");
        });
    }

    private void addElementListener(JTable dataTable, JPanel card) {
        dataTable.getSelectionModel().addListSelectionListener((var e) -> {
            if(!e.getValueIsAdjusting()) {
                int row = dataTable.getSelectedRow();
                dataTable.clearSelection();
                if(row == -1) {
                    return;
                }

                Object[] rowData = new Object[dataTable.getColumnCount()];
                for (int i = 0; i < dataTable.getColumnCount(); i++) {
                    rowData[i] = dataTable.getValueAt(row, i);
                }

                fillFields(card, tree, "", fields, enums,0, rowData);
                ((CardLayout)changeCards.getLayout()).show(changeCards, "change");
                var cards = MainWindow.getInstance().getCards();
                ((CardLayout)cards.getLayout()).show(cards, "change");
            }
        });
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
            if(field.isEnum())
                addEnum(card, field, fields, enums);
            else
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

    public int fillFields(JPanel card, ModelTree tree, String pref, Map<String, Object> fields,  Map<String, Object> enums, int col, Object[] values) {
        for(var field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                if(field.getName().equals("id")) {
                    modelId = Converter.convert(int.class, values[col].toString());
                }
                col++;
                continue;
            }
            if(!field.isPrimitive()) {
                col = fillFields(card, field, pref + field.getName() + ".", (Map<String, Object>) fields.get(field.getName()), (Map<String, Object>) enums.get(field.getName()), col, values);
                continue;
            }

            if(!field.isEnum())
                ((JTextField)fields.get(field.getName())).setText(values[col].toString());
            else
                ((JComboBox<String>)enums.get(field.getName())).setSelectedItem(values[col].toString());

            col++;
        }
        return col;
    }

    private int modelValues(ModelTree tree, Map<String, Object> fields, Object[] data, int col) {
        for(var field : tree.getFields()) {
            if(field.isIgnoreInput()) {
                data[col] = fields.get(field.getName());
                col++;
                continue;
            }
            if(!field.isPrimitive()) {
                col = modelValues(field, ((Model)fields.get(field.getName())).getValues(), data, col);
                continue;
            }
            data[col] = fields.get(field.getName());
            col++;
        }
        return col;
    }

    public void addTextField(JPanel panel, Map<String, Object> fields, String name) {
        var field = new JTextField();
        field.setUI(new CustomTextFieldUI());
        fields.put(name, field);
        fieldsNames.add(field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    public void addUpdateButton(JPanel panel, String name) {
        var button = new JButton(name);
        buttons.put(button, "update");
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            ArrayList<String> values = new ArrayList<>();
            readFields(tree, fields, enums, values);
            ArrayReader reader = new ArrayReader();
            reader.setStream(values.toArray(String[]::new));
            builder.setReader(reader, ManagerMode.GUI);
            commandManager.executeCommand("update", String.valueOf(modelId));
        });
    }

    public void addDeleteButton(JPanel panel, String name) {
        var button = new JButton(name);
        buttons.put(button, "remove");
        button.setUI(new CustomButtonUI());
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        button.addActionListener((var e) -> {
            ArrayList<String> values = new ArrayList<>();
            readFields(tree, fields, enums, values);
            ArrayReader reader = new ArrayReader();
            reader.setStream(values.toArray(String[]::new));
            builder.setReader(reader, ManagerMode.GUI);
            commandManager.executeCommand("remove_by_id", String.valueOf(modelId));
        });
    }

    public void addButton(JPanel panel, String name) {
        var button = new JButton(name);
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
        answer.setText("");
        if(response.getStatus() != Status.OK) {
            answer.setForeground(Color.RED);
        } else {
            answer.setForeground(new Color(0xE7ECF1));
        }
        if(response.get("model") != null) {
            JTable dataTable = MainWindow.getInstance().getDataTable();
            Object[] data = new Object[dataTable.getColumnCount()];
            modelValues(tree, ((Model)response.get("model")).getValues(), data, 0);
            fillFields(changeCard, tree, "", fields,  enums, 0, data);
            ((CardLayout)changeCards.getLayout()).show(changeCards, "change");
        } else {
            answer.setText(splitString(response.getAnswer()));
        }
    }

    public JPanel getChangeCards() {
        return changeCards;
    }

    public static ChangeView getInstance() {
        return instance;
    }
}
