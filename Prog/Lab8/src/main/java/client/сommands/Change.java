package client.сommands;

import client.gui.commandsView.AddView;
import client.gui.commandsView.ChangeView;
import client.utils.input.BuilderStrategy;
import common.utils.ModelTree;

import java.util.HashMap;
import java.util.Map;

public class Change extends Command {
    private final ModelTree tree;
    private final BuilderStrategy builder;


    public Change(ModelTree tree, BuilderStrategy builder) {
        super("change", "Добавляет новый элемент в коллекцию");
        this.tree = tree;
        this.builder = builder;
        this.view = new ChangeView(this, tree, builder);
        visible = true;
    }
    @Override
    void execute(String[] args) throws IllegalArgumentException {

    }
}
