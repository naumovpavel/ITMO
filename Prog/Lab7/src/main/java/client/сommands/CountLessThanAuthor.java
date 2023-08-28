package client.сommands;

import common.Commands;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import common.utils.ModelTree;
import client.utils.input.Builder;
import common.models.Person;

/**
 * Count less than author command
 */
public class CountLessThanAuthor extends Command {
    private final ModelTree tree;
    private final Builder builder;

    public CountLessThanAuthor(Builder builder) {
        super("count_less_than_author author", "Выводит количество элементов, значение поля author которых меньше заданного");
        this.tree = new ModelTree(Person.class);
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        Request request = new Request(Commands.COUNT_LESS_THAN_AUTHOR).put("model", builder.build(tree));
        Response response = client.sendAndReceive(request);

        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return;
        }

        System.out.println(response.get("count").toString());
    }
}
