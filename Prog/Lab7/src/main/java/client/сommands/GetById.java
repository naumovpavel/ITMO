package client.сommands;

import client.utils.input.Builder;
import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.Converter;
import common.utils.ModelTree;

public class GetById extends Command{
    private final ModelTree tree;
    private final Builder builder;

    public GetById(ModelTree tree, Builder builder) {
        super("add", "Добавляет новый элемент в коллекцию");
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

        if(response.get("model") == null) {
            System.out.println(response.getAnswer());
            return;
        }

        System.out.println(response.get("model").toString());
    }
}
