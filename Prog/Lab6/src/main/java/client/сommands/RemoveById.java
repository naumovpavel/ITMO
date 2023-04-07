package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Converter;

/**
 * Remove by id command
 */
public class RemoveById extends Command {

    public RemoveById() {
        super("remove_by_id id", "Удаляет элемент с заданным id");
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            throw new IllegalArgumentException("Вы не ввели значение id");
        }
        Long id = Converter.convert(Long.class, args[1]);
        Request request = new Request(Commands.REMOVE_BY_ID).put("id", id);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.getAnswer());
    }
}
