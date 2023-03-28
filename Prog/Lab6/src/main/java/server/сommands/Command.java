package server.сommands;

import common.request.Request;
import common.response.Response;

/**
 * Abstract class of command
 */
public abstract class Command {
    private final String name;
    private final String description;

    /**
     * Default constructor for command
     * @param name command name
     * @param description command description
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * Method that executes command with arguments
     * @throws IllegalArgumentException thorws if arguments isn't valid
     */
    abstract Response execute(Request request);

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
}
