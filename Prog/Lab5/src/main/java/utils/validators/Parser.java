package utils.validators;

public interface Parser<T>  {
    T parse(String value) throws Exception;
}
