package common;

public enum Commands {
    ADD("add"),
    SHOW("show"),
    CLEAR("clear"),
    COUNT_LESS_THAN_AUTHOR("count_less_than_author"),
    GET_BY_ID("get_by_id"),
    UPDATE("update"),
    HEAD("head"),
    INFO("info"),
    MAX_BY_NAME("max_by_name"),
    PRINT_FIELD_DESCENDING_TUNED_IN_WORKS("print_field_descending_tuned_in_works"),
    REMOVE_BY_ID("remove_by_id"),
    REMOVE_HEAD("remove_head"),
    SAVE("save"),
    EXIT("exit"),
    LOGIN("login"),
    GET_EVENTS("get_events"),
    REGISTER("register"),
    LOGOUT("logout"),
    REMOVE_LOWER("remove_lower");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
