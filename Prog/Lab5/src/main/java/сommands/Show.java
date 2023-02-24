package сommands;

import handlers.CollectionHandler;

public class Show extends Command {
    private final CollectionHandler<?, ?> handler;

    public Show(CollectionHandler<?, ?> handler) {
        super("show", "Выводит все элементы коллекции");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(handler.getCollection().size() == 0) {
            System.out.println("Коллекция пуста");
        }
        for(var x : handler.getCollection()) {
            System.out.println(x);
        }
    }
}
