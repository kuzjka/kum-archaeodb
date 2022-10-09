package ua.kuzjka.kumarchaeo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointNumberTest {
    @Test
    public void testSimpleToString() {
        PointNumber number = new PointNumber(234);
        assertEquals("234", number.toString());
    }

    @Test
    public void testComplexToString() {
        PointNumber number = new PointNumber(345, 77);
        assertEquals("345/77", number.toString());
    }

    @Test
    public void testParseSimple() {
        PointNumber number = PointNumber.parseNumber("345");
        assertEquals(345, number.getNumber());
        assertEquals(0, number.getSubNumber());
    }

    @Test
    public void testParseComplex() {
        PointNumber number = PointNumber.parseNumber("456/77");
        assertEquals(456, number.getNumber());
        assertEquals(77, number.getSubNumber());
    }

    @Test
    public void testParseInvalid() {
        String value = "aaa/bc";
        assertThrows(IllegalArgumentException.class, () -> PointNumber.parseNumber(value));
    }

    @Test
    public void testParseInvalidPattern() {
        String value = "111/22/33";
        assertThrows(IllegalArgumentException.class, () -> PointNumber.parseNumber(value));
    }
}
