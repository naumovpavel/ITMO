package client.сommands;

import common.Commands;
import common.request.CountLessThanAuthorRequest;
import common.request.Request;
import common.response.CountLessThanAuthorResponse;
import common.response.Response;
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
        CountLessThanAuthorRequest request = new CountLessThanAuthorRequest(builder.build(tree));
        CountLessThanAuthorResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getCount());
        }
    }
}
