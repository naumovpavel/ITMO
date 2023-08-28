package client.gui.commandsView;

import client.utils.input.BuilderStrategy;
import client.сommands.Command;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;

import java.awt.*;

public class CountLessThanAuthorView extends AddView {

    public CountLessThanAuthorView(Command command, ModelTree tree, BuilderStrategy builder) {
        super(command, tree, builder);
    }

    @Override
    public void renderResponse(Response response) {
        if(response.getStatus() != Status.OK) {
            answer.setForeground(Color.RED);
            answer.setText(response.getAnswer());
        } else {
            answer.setText(splitString(response.get("count").toString()));
            answer.setForeground(new Color(0xE7ECF1));
        }
    }
}
