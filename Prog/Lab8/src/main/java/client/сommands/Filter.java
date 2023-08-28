package client.сommands;

import client.gui.commandsView.FilterView;
import common.utils.ModelTree;

public class Filter extends Command {

    public Filter(ModelTree tree) {
        super("filter", "");
        view = new FilterView(this, tree);
        visible = true;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {

    }
}
