package com.tomlegodais.app.util;

@FunctionalInterface
public interface UnsafeFunction<T, R> {

    R applyUnsafe(T t) throws Exception;
}
