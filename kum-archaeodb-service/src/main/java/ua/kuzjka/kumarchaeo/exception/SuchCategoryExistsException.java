package ua.kuzjka.kumarchaeo.exception;

public class SuchCategoryExistsException extends RuntimeException{
    private String message;

    public SuchCategoryExistsException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
