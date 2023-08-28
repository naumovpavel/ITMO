package server.сommands;

import server.handlers.CollectionHandler;

/**
 * Info command
 */
public class Info extends Command {
    private final CollectionHandler<?, ?> handler;

    public Info(CollectionHandler<?, ?> handler) {
        super("info", "Выводит информацию о коллекции");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        System.out.printf("Дата инициализации : %s, тип : %s, количество элементов %d", handler.getInitializationDate(), handler.getType().getSimpleName(), handler.getCollection().size());
    }
}
