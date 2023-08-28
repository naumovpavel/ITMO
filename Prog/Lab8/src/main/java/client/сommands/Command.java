package client.сommands;

import client.gui.commandsView.CommandView;
import client.network.Client;
import client.network.TcpClient;
import common.network.Status;
import common.network.Request;
import common.network.Response;

/**
 * Abstract class of command
 */
public abstract class Command {
    protected final Client client;
    private final String name;
    private final String description;
    protected CommandView view;
    protected boolean visible = false;

    /**
     * Default constructor for command
     * @param name command name
     * @param description command description
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.client = TcpClient.getInstance();
    }


    /**
     * Method that executes command with arguments
     * @param args arguments
     * @throws IllegalArgumentException thorws if arguments isn't valid
     */
    abstract void execute(String[] args) throws IllegalArgumentException;

    /**
     * Returns command name
     * @return command name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns command description
     * @return command description
     */
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unchecked")
    protected <T> T handleResponse(Request request) {
        Response response = client.sendAndReceive(request);
        if(response.getStatus() != Status.OK) {
            System.out.println(response.getError());
            return null;
        }
        return (T) response;
    }

    public void render() {
        if(view == null)
            return;
        view.render();
    }

    public boolean isVisible() {
        return visible;
    }

    public void changeLocale() {
        view.changeLocale();
    }
}
