package server.сommands;

import common.models.User;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.auth.AuthManager;
import server.auth.LoginException;
import server.auth.RegisterException;

public class Register extends Command {
    private final AuthManager authManager;

    public Register(AuthManager authManager) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.authManager = authManager;
    }

    @Override
    public Response execute(Request request) {
        User user = request.get("user");
        try {
            String token = authManager.registerUser(request.getAddress(), user.getName(), user.getPassword());
            Response response = new Response("Пользователь успешно авторизовался", Status.OK);
            response.put("token", token);
            return response;
        } catch (RegisterException e) {
            return new Response(e.getMessage(), Status.ERROR);
        }
    }
}
