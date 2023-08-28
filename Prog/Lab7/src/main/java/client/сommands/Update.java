package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Converter;
import common.utils.ModelTree;
import client.utils.input.Builder;

/**
 * Update command
 */
public class Update extends Command {
    private final ModelTree tree;
    private final Builder builder;

    public Update(ModelTree tree, Builder builder) {
        super("update id", "Обновляет значение элемента коллекции, id которого равен заданному");
        this.tree = tree;
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            throw new IllegalArgumentException("Вы не ввели значение id");
        }
        int id = Converter.convert(int.class, args[1]);

        Request request = new Request(Commands.GET_BY_ID).put("id", id);
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        Request updateRequest = new Request(Commands.UPDATE).put("id", id).put("model", builder.update(tree, response.get("model")));
        System.out.println(request);
        Response updateResponse = client.sendAndReceive(updateRequest);

        if(updateResponse.getStatus() != Status.OK) {
            System.out.println(updateResponse.getError());
            return;
        }

        System.out.println(updateResponse.getAnswer());
    }
}
