package ua.kuzjka.kumarchaeo.dto;

/**
 * Represents a simple error message response.
 */
public class ErrorResponseDto {
    private String message;

    /**
     * Creates a new response object.
     * @param message   Error message
     */
    public ErrorResponseDto(String message) {
        this.message = message;
    }

    /**
     * Gets error message.
     * @return  Error message
     */
    public String getMessage() {
        return message;
    }
}
