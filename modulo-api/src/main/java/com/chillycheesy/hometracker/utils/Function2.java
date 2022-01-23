package com.chillycheesy.hometracker.utils;

@FunctionalInterface
public interface Function2<T, T2, R> {
    R apply(T arg, T2 arg2);
}
