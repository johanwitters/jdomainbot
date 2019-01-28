package com.johanw.jdomainbot.model.selector;

import com.johanw.jdomainbot.model.selector.Select;

public abstract class WithLength implements Select {
    int len;

    public WithLength(int len) {
        this.len = len;
    }
}
