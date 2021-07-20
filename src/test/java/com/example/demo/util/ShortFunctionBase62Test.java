package com.example.demo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortFunctionBase62Test {

    @DisplayName("encode() 125 -> 21")
    @Test
    void encode_25() {
        assertEquals("21", ShortFunctionBase62.encode(125));
    }

    @DisplayName("encode() 91574294826053 -> Q0DRQksv")
    @Test
    void encode_91574294826053() {
        assertEquals("Q0DRQksv", ShortFunctionBase62.encode(91574294826053L));
    }

    @DisplayName("encode() 0 - > null")
    @Test
    void encode_0() {
        assertNull(ShortFunctionBase62.encode(0));
    }

    @DisplayName("encode() negative number - > null")
    @Test
    void encode_negative() {
        assertNull(ShortFunctionBase62.encode(-5));
    }

    @DisplayName("decode() 21 -> 125")
    @Test
    void decode_21() {
        assertEquals(125, ShortFunctionBase62.decode("21"));
    }

    @DisplayName("decode() Q0DRQksv -> 91574294826053")
    @Test
    void decode_Q0DRQksv() {
        assertEquals(91574294826053L, ShortFunctionBase62.decode("Q0DRQksv"));

    }

    @DisplayName("decode() null string -> -1")
    @Test
    void decode_null() {
        assertEquals(-1, ShortFunctionBase62.decode(null));

    }

    @DisplayName("decode() empty string -> -1")
    @Test
    void decode_empty() {
        assertEquals(-1, ShortFunctionBase62.decode(""));
    }

    @DisplayName("decode() invalid symbols -> -1")
    @Test
    void decode_invalidSymbols() {
        assertEquals(-1, ShortFunctionBase62.decode("-5"));
        assertEquals(-1, ShortFunctionBase62.decode("$%#"));
    }

    @DisplayName("decode() 0 - > -1")
    @Test
    void decode_0() {
        assertEquals(-1, ShortFunctionBase62.decode("0"));
    }
}