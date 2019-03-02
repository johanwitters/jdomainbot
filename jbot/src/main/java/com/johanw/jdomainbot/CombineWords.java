package com.johanw.jdomainbot;

import com.johanw.jdomainbot.model.*;
import com.johanw.jdomainbot.model.selector.MaxLength;
import com.johanw.jdomainbot.util.DomainHelper;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import org.apache.commons.net.whois.WhoisClient;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// TODO:
public class CombineWords {
    public static void main1(String[] args) throws IOException {
        System.out.println("Current path: " + Paths.get("").toAbsolutePath().toString());

        List<Words> allWords = new ArrayList<>();
        allWords.add(new WordsFromFile(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/adjectives/colors.txt"));
        allWords.add(new WordsFromDirectory(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/adjectives"));
        allWords.add(new WordsFromDirectory(MaxLength.from(10), "jbot/src/main/resources/wordlists-master/nouns"));
        Words words = CombinedWords.from(allWords);

        String tld = ".com";
        String outputFile = "combined.available.txt";
        System.out.println("Current path: " + Paths.get("").toAbsolutePath().toString());
        System.out.println("tld: " + tld);
        System.out.println("output fileName: " + outputFile);

        File file = new File(outputFile);
        file.delete();

        CombineWords obj = new CombineWords(tld, words, outputFile);
        obj.run();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Current path: " + Paths.get("").toAbsolutePath().toString());

//        Words words1 = new WordsFromFile(MaxLength.from(11), "jbot/src/main/resources/wordlists-master/adjectives/colors.txt");
//        Words words1 = new WordsFromDirectory(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/adjectives");
//        Words words1 = new WordsFromDirectory(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/nouns");
//        Words words1 = new WordsFromDirectory(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/names");
//        Words words1 = new WordsFromDirectory(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/verbs");
        Words words1 = new WordsFromDirectory(MaxLength.from(3), "jbot/src/main/resources/wordlists-master/names");
        Words words2 = new SingleWord("ig");
        Words words = CombinedWords.from(words1, words2);

        String tld = ".com";
        String outputFile = "combined.available.txt";
        System.out.println("Current path: " + Paths.get("").toAbsolutePath().toString());
        System.out.println("tld: " + tld);
        System.out.println("output fileName: " + outputFile);

        File file = new File(outputFile);
        file.delete();

        CombineWords obj = new CombineWords(tld, words, outputFile);
        obj.run();
    }

    private String tld;
    private Words words;
    private Path outputFile;
    private int totalFound;

    public CombineWords(String tld, Words words, String outputFile) {
        this.tld = tld;
        this.words = words;
        this.outputFile = Paths.get(outputFile);
        this.totalFound = 0;
    }

    private void writeToFile(String domainName) {
        try {
            totalFound++;
            Files.write(outputFile, (domainName + "\r\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("io exception whilst writing to " + outputFile.getFileName());
        }
    }

    public void run() {
        System.out.println();
        System.out.println("Available domains:");
        ProgressBarBuilder pbb = new ProgressBarBuilder().setInitialMax(words.size()).showSpeed();

        ProgressBar.wrap(words, pbb).forEachRemaining(domainName -> {
            if (DomainHelper.isAvailable(domainName + tld)) {
                writeToFile(domainName + tld);
            }
        });
        System.out.println();
        System.out.println("Done");

    }

}
