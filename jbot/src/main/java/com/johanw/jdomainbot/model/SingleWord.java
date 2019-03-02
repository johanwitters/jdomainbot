package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.model.selector.Select;

public class SingleWord extends InMemoryWords {
    public SingleWord(String word) {
        super(null);
        add(word);
    }
}
