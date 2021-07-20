package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortFunctionTest {

    @Test
    void generate() {
        assertEquals(6, ShortFunction.generate().length());
    }
}