package com.hamzajg.tddandroid;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * One test per rule of the kata, written in the order the rules were added.
 */
public class StringCalculatorTest {

    private StringCalculator calculator;

    @Before
    public void setUp() {
        calculator = new StringCalculator();
    }

    @Test
    public void emptyStringReturnsZero() {
        assertEquals(0, calculator.add(""));
    }

    @Test
    public void nullReturnsZero() {
        assertEquals(0, calculator.add(null));
    }

    @Test
    public void oneNumberReturnsThatNumber() {
        assertEquals(6, calculator.add("6"));
    }

    @Test
    public void twoNumbersAreSummed() {
        assertEquals(3, calculator.add("1,2"));
    }

    @Test
    public void manyNumbersAreSummed() {
        assertEquals(45, calculator.add("1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void newlineIsAlsoADelimiter() {
        assertEquals(6, calculator.add("1\n2,3"));
    }

    @Test
    public void customDelimiterIsSupported() {
        assertEquals(3, calculator.add("//;\n1;2"));
    }

    @Test
    public void negativeNumbersAreRejected() {
        try {
            calculator.add("1,-2");
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("-2"));
        }
    }

    @Test
    public void everyNegativeNumberIsReported() {
        try {
            calculator.add("-1,2,-3");
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("-1"));
            assertTrue(e.getMessage().contains("-3"));
        }
    }

    @Test
    public void numbersGreaterThanOneThousandAreIgnored() {
        assertEquals(2, calculator.add("2,1001"));
    }

    @Test
    public void oneThousandIsIncluded() {
        assertEquals(1000, calculator.add("1000"));
    }
}
