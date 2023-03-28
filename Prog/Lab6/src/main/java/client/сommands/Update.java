package client.сommands;

import common.Commands;
import common.request.GetByIdRequest;
import common.request.Request;
import common.request.UpdateRequest;
import common.response.GerByIdResponse;
import common.response.Response;
import common.response.UpdateResponse;
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
        Long id = Converter.convert(Long.class, args[1]);

        GetByIdRequest request = new GetByIdRequest(id);
        GerByIdResponse response =  handleResponse(request);

        if(response == null) {
            return;
        }

        UpdateRequest updateRequest = new UpdateRequest(builder.update(tree, response.getModel()), id);
        UpdateResponse updateResponse =  handleResponse(updateRequest);

        if(updateResponse != null) {
            System.out.println(response.getAnswer());
        }
    }
}
