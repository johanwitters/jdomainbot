package com.johanw.jdomainbot.model.selector;

public class MaxLength extends WithLength {
    public MaxLength(int len) {
        super(len);
    }

    @Override
    public boolean include(String value) {
        return value.length() <= len;
    }

    public static Select from(int value) {
        return new MaxLength(value);
    }
}
