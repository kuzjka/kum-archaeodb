package ua.kuzjka.kumarchaeo.dto;

public class ErrorMessageDto {
    private String message;
        public ErrorMessageDto(String message) {
        this.message = message;
            }
    public String getMessage() {
        return message;
    }

}
