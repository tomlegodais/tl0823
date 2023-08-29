package com.tomlegodais.app.util;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class SafeFunctionTest {

    @Test
    public void applyReturnsResultWhenNoException() {
        var safeFunction = SafeFunction.of((String s) -> Integer.parseInt(s));
        var result = safeFunction.apply("1");

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void applyReturnsNullWhenException() {
        var safeFunction = SafeFunction.of((String s) -> Integer.parseInt(s));
        var result = safeFunction.apply("abc");

        assertThat(result).isNull();
    }

    @Test
    public void applyReturnsNullWhenCheckedException() {
        var safeFunction = SafeFunction.of(s -> {
            throw new Exception("Some checked exception");
        });

        var result = safeFunction.apply("anything");
        assertThat(result).isNull();
    }
}
