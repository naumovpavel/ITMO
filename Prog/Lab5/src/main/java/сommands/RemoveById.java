package сommands;

import handlers.CollectionHandler;
import utils.Converter;

public class RemoveById extends Command {
    private final CollectionHandler<?, ?> handler;

    public RemoveById(CollectionHandler<?, ?> handler) {
        super("remove_by_id id", "Удаляет элемент с заданным id");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            throw new IllegalArgumentException("Вы не ввелм значение id");
        }
        Long id = Converter.convert(Long.class, args[1]);
        if(handler.removeById(id)) {
            System.out.println("Элемент успешно удален");
        } else {
            System.out.println("Элемента с таким id не было в коллекции");
        }
    }
}
