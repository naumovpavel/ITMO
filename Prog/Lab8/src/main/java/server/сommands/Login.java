package server.сommands;

import common.models.Model;
import common.models.User;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utils.ModelTree;
import server.auth.AuthManager;
import server.auth.LoginException;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

public class Login extends Command {
    private AuthManager authManager;

    public Login(AuthManager authManager) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.authManager = authManager;
    }

    @Override
    public Response execute(Request request) {
        User user = request.get("user");
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        try {
            String token = authManager.loginUser(request.getAddress(), user.getName(), user.getPassword());
            Response response = new Response(r.getString("login_ok"), Status.OK);
            response.put("token", token);
            return response;
        } catch (LoginException e) {
            return new Response(r.getString(e.getMessage()), Status.ERROR);
        }
    }
}
