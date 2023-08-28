package client.сommands;

import client.gui.commandsView.SortView;
import common.utils.ModelTree;

public class Sort extends Command {

    public Sort(ModelTree tree) {
        super("sort", "");
        view = new SortView(this, tree);
        visible = true;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {

    }
}
