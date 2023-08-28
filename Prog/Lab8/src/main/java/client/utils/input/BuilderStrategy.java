package client.utils.input;

import client.сommands.ManagerMode;

public class BuilderStrategy {
    private ManagerMode mode;
    private Builder builder;
    private Reader reader;

    public BuilderStrategy() {
    }

    public void setReader(Reader reader, ManagerMode mode) {
        switch (mode) {
            case User -> builder = new CLIBuilder(reader);
            case NoUser -> builder = new NoUserCLIBuilder((BufferedReader) reader);
            case GUI -> builder = new GUIBuilder(reader);
        }

        this.mode = mode;
    }

    public Reader getReader() {
        return reader;
    }

    public Builder getBuilder() {
        return builder;
    }
}
