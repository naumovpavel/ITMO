package client.gui.frames;

import client.gui.ui.*;
import client.main.Main;
import client.сommands.CommandManager;
import common.utils.ModelTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame implements ItemListener {
    private JPanel mainPanel;
    private JPanel topPanel;
    public DefaultTableModel tableModel;
    private Table tablePanel;
    private JPanel menuPanel;
    public JLabel usernameLabel;
    private JButton logoutButton;
    private JButton visualizeButton;
    private JComboBox<String> languageComboBox;
    private JPanel mapMenu;
    private JPanel visualizePanel;
    private String visualize = "table";
    private ModelTree tree;
    private CommandManager commandManager;
    private JComboBox<String> cb;
    JPanel cards;
    private CardLayout cardLayout;
    private JComboBox<String> cardComboBox;
    private static MainWindow instance;

    public MainWindow(CommandManager commandManager, ModelTree tree) {
        MainWindow.instance = this;
        this.tree = tree;
        this.commandManager = commandManager;
    }

    public void render() {
        setTitle("Divided Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        setMainPanel();
        setTopPanel();
        setVisualizeButton();
        setVisualizePanel();
        setMenuPanel();

        commandManager.getPublisher().run();
        commandManager.executeCommand("show");
    }

    public void changeLocale() {
        var r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        logoutButton.setText(r.getString("logout"));
        visualizeButton.setText(r.getString("visualize"));
        visualizeButton.setText(r.getString("visualize"));
        cb.repaint();

        for(var command : commandManager.getCommands().values()) {
            if(command.isVisible()) {
                SwingUtilities.invokeLater(command::changeLocale);
            }
        }
    }

    private void setMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x242F3D));
        setContentPane(mainPanel);
    }

    private void setTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(getWidth(), 75));
        topPanel.setBackground(new Color(0x242F3D));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        setUsernameLabel();
        setLogoutButton();
        setLanguageComboBox();
    }

    private void setUsernameLabel() {
        usernameLabel = new JLabel("username");
        usernameLabel.setUI(new CustomLabelUI());
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(usernameLabel, BorderLayout.WEST);
        topPanel.add(Box.createHorizontalStrut(20));
    }

    private void setLogoutButton() {
        logoutButton = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("logout"));
        logoutButton.setUI(new CustomButtonUI());
        logoutButton.addActionListener((ActionEvent e) -> {
            commandManager.executeCommand("logout", "");
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
        topPanel.add(Box.createHorizontalStrut(20));
    }

    private void setLanguageComboBox() {
        String[] languages = {"en_nz", "lv_lv", "et_ee", "ru_RU"};
        languageComboBox = new JComboBox<>(languages);
        setCBUI(languageComboBox);
        languageComboBox.setSelectedItem(Locale.getDefault().toString());
        languageComboBox.addItemListener(e -> {
            //Locale.setDefault(new Locale((String) e.getItem()));
            Main.locale = new Locale((String) e.getItem());
            changeLocale();
        });
        topPanel.add(languageComboBox);
        topPanel.add(Box.createHorizontalStrut(20));
    }


    private void setVisualizeButton() {
        visualizeButton = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("visualize"));
        visualizeButton.setUI(new CustomButtonUI());
        visualizeButton.addActionListener((ActionEvent e) -> {
            CardLayout layout = (CardLayout) visualizePanel.getLayout();
            if(visualize.equals("map")) {
                layout.show(visualizePanel, "table");
                visualize = "table";
            } else {
                visualize = "map";
                layout.show(visualizePanel, "map");
            }
        });
        topPanel.add(visualizeButton, BorderLayout.EAST);
        topPanel.add(Box.createHorizontalStrut(20));
    }

    private void setVisualizePanel() {
        visualizePanel = new JPanel(new CardLayout());
        mainPanel.add(visualizePanel, BorderLayout.CENTER);
        setTablePanel();
        setMapPanel();
        ((CardLayout)visualizePanel.getLayout()).show(visualizePanel, "table");
    }

    private void setTablePanel() {
        tablePanel = new Table(tree);
        visualizePanel.add(tablePanel, "table");
        commandManager.getPublisher().addSubscriber(tablePanel);
    }

    private void setMapPanel() {
        ModelsMap map = new ModelsMap(commandManager);
        visualizePanel.add(map, "map");
        commandManager.getPublisher().addSubscriber(map);
    }

    private void setMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(0x17212B));
        menuPanel.setPreferredSize(new Dimension(330, getHeight()));
        mainPanel.add(menuPanel, BorderLayout.EAST);
        renderMenu();
    }


    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    public void renderMenu() {
        ArrayList<String> commands = new ArrayList<>();
        ResourceBundle r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        for(var command : commandManager.getCommands().values()) {
            if(command.isVisible()) {
                SwingUtilities.invokeLater(command::render);
                commands.add(command.getName());
            }
        }
        var cb = new JComboBox<String>(commands.toArray(String[]::new));
        cb.setRenderer(new CustomListCellRenderer());
        setCBUI(cb);
        this.cb = cb;
        cb.addItemListener(this);

        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);

        menuPanel.add(cb, BorderLayout.NORTH);
        menuPanel.add(cards, BorderLayout.CENTER);
    }

    static class CustomListCellRenderer extends DefaultListCellRenderer {
        public CustomListCellRenderer() {
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            return super.getListCellRendererComponent(list, ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString(value.toString()), index, isSelected, cellHasFocus);
        }
    }

    private void setCBUI(JComboBox<?> cb) {
        cb.setFont(new Font("Arial", Font.PLAIN, 14));
        cb.setForeground(new Color(0xE7ECF1));
        cb.setBackground(new Color(0x3E546A));
        cb.setMaximumSize(new Dimension(100, 30));
        cb.setPreferredSize(new Dimension(100, 30));
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        cb.setEditable(false);
        cb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    public void addColumns(ModelTree tree, DefaultTableModel table, String pref) {
        for(var field : tree.getFields()) {
            if(!field.isPrimitive()) {
                addColumns(field, table, pref + field.getName() + ".");
                continue;
            }

            table.addColumn(pref + field.getName());
        }
    }

    public static MainWindow getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) tablePanel.getDataTable().getModel();
    }

    public Table getTablePanel() {
        return tablePanel;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JComboBox<String> getLanguageComboBox() {
        return languageComboBox;
    }

    public JTable getDataTable() {
        return tablePanel.getDataTable();
    }

    public JPanel getMapMenu() {
        return mapMenu;
    }

    public JPanel getCards() {
        return cards;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JComboBox<String> getCardComboBox() {
        return cardComboBox;
    }
}
