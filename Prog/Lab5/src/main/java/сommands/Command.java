package сommands;

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
}
