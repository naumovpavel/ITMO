package client.сommands;

import client.utils.input.Builder;
import common.Commands;
import common.request.GetByIdRequest;
import common.request.Request;
import common.response.GerByIdResponse;
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
        Long id = Converter.convert(Long.class, args[1]);

        GetByIdRequest request = new GetByIdRequest(id);
        GerByIdResponse response =  handleResponse(request);

        if(response != null) {
            System.out.println(response.getModel());
        }
    }
}
