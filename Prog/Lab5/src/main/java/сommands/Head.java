package сommands;

import handlers.CollectionHandler;

public class Head extends Command {
    private final CollectionHandler<?, ?> handler;

    public Head(CollectionHandler<?, ?> handler) {
        super("head", "Выводит первый элемент в коллекции");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(handler.getHead() != null) {
            System.out.println(handler.getHead());
        } else {
            System.out.println("Коллекция пуста");
        }
    }
}
