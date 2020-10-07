package fontys.library.exceptions;

public class BookExistsException extends Exception {
    public BookExistsException() {}

    public BookExistsException(String message) {
        super(message);
    }
}
