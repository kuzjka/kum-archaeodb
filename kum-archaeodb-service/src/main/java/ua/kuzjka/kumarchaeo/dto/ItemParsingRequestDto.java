package ua.kuzjka.kumarchaeo.dto;

import ua.kuzjka.kumarchaeo.model.Delimiter;

public class ItemParsingRequestDto {

    private String data;
    private Delimiter delimiter;

    private boolean commaDecimalSeparators;

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

    public boolean isCommaDecimalSeparators() {
        return commaDecimalSeparators;
    }

    public void setCommaDecimalSeparators(boolean commaDecimalSeparators) {
        this.commaDecimalSeparators = commaDecimalSeparators;
    }
}
