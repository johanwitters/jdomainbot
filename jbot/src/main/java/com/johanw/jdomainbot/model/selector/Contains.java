package com.johanw.jdomainbot.model.selector;

public class Contains extends WithString {
    public Contains(String str) {
        super(str);
    }

    @Override
    public boolean include(String value) {
        return value.contains(str);
    }

    public static Select from(String str) {
        return new Contains(str);
    }
}
