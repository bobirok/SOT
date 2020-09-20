package src.main.java.library.exceptions;

public class BookOutOfOrderException extends Exception {
    public BookOutOfOrderException() {}

    public BookOutOfOrderException(String message) {
        super(message);
    }
}
