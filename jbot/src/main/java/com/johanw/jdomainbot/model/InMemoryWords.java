package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.model.selector.Select;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class InMemoryWords implements Words {
    private List<String> words = null;
    private int pos;
    private Select select;

    public void reset() {
        pos = 0;
    }

    public InMemoryWords(Select select) {
        this.words = new ArrayList<>();
        this.select = select;
        reset();
    }

    protected void add(String value) {
        if (!words.contains(value)) {
            if (select != null) {
                if (select.include(value))
                    words.add(value);
            } else {
                words.add(value);
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (words == null) return false;
        return pos < words.size();
    }

    @Override
    public String next() {
        if (words == null) return null;
        pos++;
        return words.get(pos - 1);
    }

    public long size() {
        return words.size();
    }
}
