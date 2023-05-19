package com.tomas.bankingprogram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitTest {
    ExampleToTest exampleToTest;
    @BeforeEach
    void setUp() {
        exampleToTest = new ExampleToTest();
    }

    @Test
    @DisplayName("Multiplication should work")
    void testMultiply() {
        assertEquals(20, exampleToTest.multiply(4, 5),
            "Regular multiplication should work"
        );
    }

    @RepeatedTest(5)
    @DisplayName("ensure correct hangling of zero")
    void testMultiplyWithZero() {
        assertEquals(0, exampleToTest.multiply(0, 5),
            "Multiple with zero should be zero"
        );

        assertEquals(0, exampleToTest.multiply(5, 0),
                "Multiple with zero should be zero"
        );
    }
}
