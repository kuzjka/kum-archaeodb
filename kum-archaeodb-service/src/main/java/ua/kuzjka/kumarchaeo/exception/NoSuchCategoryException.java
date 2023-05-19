package ua.kuzjka.kumarchaeo.exception;

public class NoSuchCategoryException extends RuntimeException{

    private String message;

    public NoSuchCategoryException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
