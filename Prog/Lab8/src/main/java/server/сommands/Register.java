package server.сommands;

import common.models.User;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.auth.AuthManager;
import server.auth.LoginException;
import server.auth.RegisterException;

import java.util.ResourceBundle;

public class Register extends Command {
    private final AuthManager authManager;

    public Register(AuthManager authManager) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.authManager = authManager;
    }

    @Override
    public Response execute(Request request) {
        User user = request.get("user");
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        try {
            String token = authManager.registerUser(request.getAddress(), user.getName(), user.getPassword());
            Response response = new Response(r.getString("register_ok"), Status.OK);
            response.put("token", token);
            return response;
        } catch (RegisterException e) {
            return new Response(r.getString(e.getMessage()), Status.ERROR);
        }
    }
}
