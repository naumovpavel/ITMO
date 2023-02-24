package сommands;

public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }


    abstract void execute(String[] args) throws IllegalArgumentException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
