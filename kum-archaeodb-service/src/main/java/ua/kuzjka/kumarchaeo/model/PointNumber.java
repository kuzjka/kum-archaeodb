package ua.kuzjka.kumarchaeo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.awt.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Map point number.
 * Complex of points share the same number and each point has a sub-number (i.e. 14/1, 14/2).
 * Sub-number '0' means there is no sub-number.
 */
@Embeddable
public class PointNumber {
    private static final Pattern PATTERN = Pattern.compile("(\\d+)(/\\d+)?");

    private int number;

    @Column(name = "sub_number")
    private int subNumber;

    /**
     * Creates new point number.
     */
    public PointNumber() {
    }

    /**
     * Creates new point number.
     * @param number    Point number
     */
    public PointNumber(int number) {
        this.number = number;
    }

    /**
     * Creates a new complex point number
     * @param number        Point number
     * @param subNumber     Point sub-number
     */
    public PointNumber(int number, Integer subNumber) {
        this.number = number;
        this.subNumber = subNumber;
    }

    /**
     * Gets point number.
     * @return  Point number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets point number.
     * @param number    Point number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets point sub-number.
     * @return  Point sub-number or {@code 0} if there is no sub-number.
     */
    public int getSubNumber() {
        return subNumber;
    }

    /**
     * Sets point sub-number.
     * @param subNumber Point sub-number or '0' to remove sub-number.
     */
    public void setSubNumber(int subNumber) {
        this.subNumber = subNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointNumber that = (PointNumber) o;

        if (number != that.number) return false;
        return subNumber == that.subNumber;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + subNumber;
        return result;
    }

    @Override
    public String toString() {
        return subNumber == 0 ? String.valueOf(number) : number + "/" + subNumber;
    }

    /**
     * Parses point number from a string representation.
     * Format is {@code number[/subnumber]}.
     * @param value String representation of point number
     * @return      Point number object
     * @throws  IllegalArgumentException    if argument does not match the point number pattern
     * @throws  NumberFormatException       if numbers in the string could not be parsed
     */
    public static PointNumber parseNumber(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) throw new IllegalArgumentException("Cannot parse point number: " + value);
        PointNumber result = new PointNumber(Integer.parseInt(matcher.group(1)));
        if (matcher.group(2) != null) result.subNumber = Integer.parseInt(matcher.group(2).substring(1));
        return result;
    }
}
