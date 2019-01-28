package com.johanw.jdomainbot.model.selector;

public class MinLength extends WithLength {
    public MinLength(int len) {
        super(len);
    }

    @Override
    public boolean include(String value) {
        return value.length() >= len;
    }

    public static Select from(int value) {
        return new MinLength(value);
    }
}
