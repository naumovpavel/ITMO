package client.сommands;

import common.Commands;
import common.request.RemoveByIdRequest;
import common.request.Request;
import common.response.CountLessThanAuthorResponse;
import common.response.RemoveByIdResponse;
import common.utils.Converter;
import server.handlers.CollectionHandler;

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
        RemoveByIdRequest request = new RemoveByIdRequest(id);
        RemoveByIdResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getAnswer());
        }
    }
}
