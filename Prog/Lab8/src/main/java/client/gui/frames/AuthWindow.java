package client.gui.frames;

import client.gui.ui.CustomButtonUI;
import client.gui.ui.CustomLabelUI;
import client.gui.ui.CustomTextFieldUI;
import client.main.Main;
import client.utils.input.ArrayReader;
import client.utils.input.BuilderStrategy;
import client.сommands.CommandManager;
import client.сommands.ManagerMode;
import client.сommands.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class AuthWindow extends JFrame {
    private JLabel helloLabel;
    private JLabel errorLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private CommandManager commandManager;
    private BuilderStrategy builder;
    private JComboBox<String> languageComboBox;
    private static AuthWindow instance;

    public AuthWindow(CommandManager commandManager) {
        this.commandManager = commandManager;
        builder = commandManager.getBuilder();
        instance = this;
        setTitle("Authentication Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0x242F3D));
        createMainPanel();
        setContentPane(mainPanel);
    }

    public void render() {
        mainPanel.removeAll();
        createHelloLabel();
        createLanguageComboBox();
        createErrorLabel();
        createEmailLabel();
        createEmailField();
        createPasswordLabel();
        createPasswordField();
        createButtonPanel();
    }

    public void changeLocale() {
        var r = ResourceBundle.getBundle("bundles.GuiLabels", Main.locale);
        helloLabel.setText(r.getString("hello"));
        emailLabel.setText(r.getString("email"));
        passwordLabel.setText(r.getString("password"));
        loginButton.setText(r.getString("login"));
        registerButton.setText(r.getString("register"));

    }

    private void createLanguageComboBox() {
        String[] languages = {"en_NZ", "lv_LV", "et_EE", "ru_RU"};
        languageComboBox = new JComboBox<>(languages);
        setCBUI(languageComboBox);
        mainPanel.add(languageComboBox);
        languageComboBox.setSelectedItem(Locale.getDefault().toString());
        languageComboBox.addItemListener(e -> {
            Main.locale = new Locale((String) e.getItem());
            changeLocale();
        });

    }

    private void setCBUI(JComboBox<?> cb) {
        cb.setFont(new Font("Arial", Font.PLAIN, 14));
        cb.setForeground(new Color(0xE7ECF1));
        cb.setBackground(new Color(0x3E546A));
        cb.setMaximumSize(new Dimension(100, 30));
        cb.setPreferredSize(new Dimension(75, 30));
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        cb.setEditable(false);
        cb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    private JPanel mainPanel;

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0x242F3D));
    }

    private void createHelloLabel() {
        helloLabel = new JLabel(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("hello"));
        helloLabel.setUI(new CustomLabelUI());
        helloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(helloLabel);
        mainPanel.add(Box.createVerticalStrut(10));
    }

    private void createErrorLabel() {
        errorLabel = new JLabel();
        errorLabel.setUI(new CustomLabelUI());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(errorLabel);
        mainPanel.add(Box.createVerticalStrut(10));
    }

    private void createEmailLabel() {
        emailLabel = new JLabel(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("email"));
        emailLabel.setUI(new CustomLabelUI());
        mainPanel.add(emailLabel);
    }

    private void createEmailField() {
        emailField = new JTextField();
        emailField.setUI(new CustomTextFieldUI());
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(10));
    }

    private void createPasswordLabel() {
        passwordLabel = new JLabel(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("password"));
        passwordLabel.setUI(new CustomLabelUI());
        mainPanel.add(passwordLabel);
    }

    private void createPasswordField() {
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setForeground(new Color(0xE7ECF1));
        passwordField.setBackground(new Color(0x3E546A));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passwordField.setMaximumSize(new Dimension(250, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(20));
    }

    private void createButtonPanel() {
        loginButton = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("login"));
        loginButton.setUI(new CustomButtonUI());
        loginButton.addActionListener((ActionEvent e) -> {
            String[] values = new String[]{emailField.getText(), passwordField.getText()};
            ArrayReader reader = new ArrayReader();
            reader.setStream(values);
            builder.setReader(reader, ManagerMode.GUI);
            commandManager.executeCommand("login", "");
        });

        registerButton = new JButton(ResourceBundle.getBundle("bundles.GuiLabels", Main.locale).getString("register"));
        registerButton.setUI(new CustomButtonUI());
        registerButton.addActionListener((ActionEvent e) -> {
            String[] values = new String[]{emailField.getText(), passwordField.getText()};
            ArrayReader reader = new ArrayReader();
            reader.setStream(values);
            builder.setReader(reader, ManagerMode.GUI);
            commandManager.executeCommand("register", "");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());
    }

    public static AuthWindow getInstance() {
        return instance;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }
}
