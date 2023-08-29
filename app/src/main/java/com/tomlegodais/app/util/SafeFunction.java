package com.tomlegodais.app.util;

@FunctionalInterface
public interface SafeFunction<T, R> {

    R applyUnsafe(T t) throws Exception;

    default R apply(T t) {
        try {
            return applyUnsafe(t);
        } catch (Exception e) {
            return null;
        }
    }

    static <T, R> SafeFunction<T, R> of(UnsafeFunction<T, R> function) {
        return function::applyUnsafe;
    }
}
