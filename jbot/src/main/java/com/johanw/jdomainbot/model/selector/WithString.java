package com.johanw.jdomainbot.model.selector;

public abstract class WithString implements Select {
    String str;

    public WithString(String str) {
        this.str = str;
    }
}
