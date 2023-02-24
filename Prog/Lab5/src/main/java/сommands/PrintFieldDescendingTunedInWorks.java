package сommands;

import handlers.CollectionHandler;

public class PrintFieldDescendingTunedInWorks extends Command {
    private final CollectionHandler<?, ?> handler;

    public PrintFieldDescendingTunedInWorks(CollectionHandler<?, ?> handler) {
        super("print_field_descending_tuned_in_works", "Выводит значения поля tunedInWorks всех элементов в порядке убывания");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(handler.getCollection().size() == 0) {
            System.out.println("Коллекция пуста");
        } else {
            handler.printFieldDescending();
        }
    }
}
