package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.model.selector.Select;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordsFromArray extends InMemoryWords {
    public WordsFromArray(String[] words) {
        this(null, words);
    }

    public WordsFromArray(Select select, String[] words) {
        super(select);
        init(words);
    }

    private void init(String[] words) {
        for(String word: words) {
            add(word);
        }
    }
}
