package ua.kuzjka.kumarchaeo.dto;

import ua.kuzjka.kumarchaeo.model.Delimiter;

public class ItemParsingRequestDto {

    private String data;
    private Delimiter delimiter;

    public ItemParsingRequestDto() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Delimiter getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(Delimiter delimiter) {
        this.delimiter = delimiter;
    }
}
