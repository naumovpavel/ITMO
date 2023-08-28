package client.gui.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JComboBox<String> comboBox;

    public CardLayoutPanel() {
        setLayout(new BorderLayout());
        setSize(new Dimension(200,200));
        // Create JComboBox
        comboBox = new JComboBox<>();
        comboBox.addItem("Card 1");
        comboBox.addItem("Card 2");
        comboBox.addItem("Card 3");
        comboBox.addActionListener(new ComboBoxListener());

        // Create Cards Panel with CardLayout
        cardsPanel = new JPanel();
        cardLayout = new CardLayout();
        cardsPanel.setLayout(cardLayout);

        // Create Card 1
        JPanel card1 = createCardPanel("Card 1");

        // Create Card 2
        JPanel card2 = createCardPanel("Card 2");

        // Create Card 3
        JPanel card3 = createCardPanel("Card 3");

        // Add cards to the cardsPanel
        cardsPanel.add(card1, "Card 1");
        cardsPanel.add(card2, "Card 2");
        cardsPanel.add(card3, "Card 3");

        // Add components to the main panel
        add(comboBox, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel createCardPanel(String cardName) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 20; i++) {
            JTextField textField = new JTextField(20);
            cardPanel.add(textField);
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        return wrapperPanel;
    }

    private class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCard = (String) comboBox.getSelectedItem();
            cardLayout.show(cardsPanel, selectedCard);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Card Layout Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CardLayoutPanel panel = new CardLayoutPanel();
            frame.getContentPane().add(panel);

            frame.setVisible(true);
        });
    }
}
