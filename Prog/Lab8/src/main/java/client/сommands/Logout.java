package client.сommands;

import client.gui.frames.AuthWindow;
import client.gui.frames.MainWindow;

import javax.swing.*;

public class Logout extends Command {

    public Logout() {
        super("login", "Добавляет новый элемент в коллекцию");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        client.setToken("");
        client.setName("");
        client.setAuthed(false);
        SwingUtilities.invokeLater(() -> {
            AuthWindow.getInstance().render();
            MainWindow.getInstance().setVisible(false);
            AuthWindow.getInstance().setVisible(true);
        });
    }
}
