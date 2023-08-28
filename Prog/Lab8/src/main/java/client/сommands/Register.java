package client.сommands;

import client.gui.frames.AuthWindow;
import client.gui.frames.MainWindow;
import client.handlers.CollectionPublisher;
import client.utils.input.BuilderStrategy;
import common.Commands;
import common.models.User;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;

import javax.swing.*;
import java.awt.*;

public class Register extends Command {
    private final ModelTree tree;
    private final BuilderStrategy builder;

    public Register(ModelTree tree, BuilderStrategy builder) {
        super("login", "Добавляет новый элемент в коллекцию");
        this.tree = tree;
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.REGISTER);
        Response response = null;

        try {
            request.put("user", builder.getBuilder().build(tree));
            response = client.sendAndReceive(request);
        } catch (IllegalArgumentException e) {
            response = new Response(e.getMessage(), Status.ERROR);
        }

        var answer = AuthWindow.getInstance().getErrorLabel();
        answer.setText("");
        if(response.getStatus() != Status.OK) {
            if(response.getStatus() != Status.OK) {
                answer.setForeground(Color.RED);
            } else {
                answer.setForeground(new Color(0xE7ECF1));
            }
            answer.setText(response.getAnswer());
            return;
        }

        client.setToken(response.get("token"));
        client.setName(((User)request.get("user")).getName());
        client.setAuthed(true);
        SwingUtilities.invokeLater(() -> {
            MainWindow.getInstance().render();
            MainWindow.getInstance().usernameLabel.setText(client.getName());
            AuthWindow.getInstance().setVisible(false);
            MainWindow.getInstance().setVisible(true);
        });
    }
}
