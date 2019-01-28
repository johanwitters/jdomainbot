package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.model.selector.Select;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordsFromFile extends InMemoryWords {
    public WordsFromFile(String fileName) throws IOException {
        this(null, fileName);
    }

    public WordsFromFile(Select select, String fileName) throws IOException {
        super(select);
        init(fileName);
    }

    private void init(String fileName) throws IOException {
        Stream<String> stream = Files.lines(Paths.get(fileName));
        stream.forEach(word -> {
            add(word);
        });
    }
}
