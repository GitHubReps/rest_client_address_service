package example.demo.exceptions;

public class ClientExistsException extends Exception {

    public ClientExistsException(String message) {
        super(message);
    }
}
