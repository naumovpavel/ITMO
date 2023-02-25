package сommands;

import handlers.CollectionHandler;
import utils.Converter;

/**
 * Remove head command
 */
public class RemoveHead extends Command {
    private final CollectionHandler<?, ?> handler;

    public RemoveHead(CollectionHandler<?, ?> handler) {
        super("remove_head", "Удаляет первый элемент коллекции");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(handler.getCollection().size() == 0) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println(handler.removeHead());
        }
    }
}
