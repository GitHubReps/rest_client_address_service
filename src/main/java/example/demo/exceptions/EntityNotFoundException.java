package example.demo.exceptions;

public class EntityNotFoundException extends Exception {

    public static EntityNotFoundException entityNotFoundException(String message) {
        return new EntityNotFoundException(message);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
