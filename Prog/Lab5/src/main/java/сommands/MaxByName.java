package сommands;

import handlers.CollectionHandler;

public class MaxByName extends Command {
    private final CollectionHandler<?, ?> handler;

    public MaxByName(CollectionHandler<?, ?> handler) {
        super("max_by_name", "Выводит любой объект из коллекции, значение поля name которого является максимальным");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(handler.getCollection().size() == 0) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println(handler.maxByName());
        }
    }
}
