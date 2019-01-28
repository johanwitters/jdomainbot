package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.model.selector.Select;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordsFromDirectory extends InMemoryWords {
    public WordsFromDirectory(String directory) throws IOException {
        this(null, directory);
    }

    public WordsFromDirectory(Select select, String directory) throws IOException {
        super(select);
        init(new String[] { directory } );
    }

    public WordsFromDirectory(String directories[]) throws IOException {
        this(null, directories);
    }

    public WordsFromDirectory(Select select, String[] directories) throws IOException {
        super(select);
        init(directories);
    }

    private void addDirectory(String directory) throws IOException {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    addFile(listOfFiles[i].getAbsolutePath());
                } else if (listOfFiles[i].isDirectory()) {
                    addDirectory(listOfFiles[i].getAbsolutePath());
                }
            }
        }
    }

    private void addFile(String fileName) throws IOException {
        Stream<String> stream = Files.lines(Paths.get(fileName));
        stream.forEach(word -> {
            add(word);
        });
    }

    private void init(String[] directories) throws IOException {
        for (String directory: directories) {
            addDirectory(directory);
        }
    }
}
