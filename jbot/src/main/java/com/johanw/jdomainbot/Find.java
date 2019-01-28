package com.johanw.jdomainbot;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.apache.commons.net.whois.WhoisClient;

public class Find {
    enum WhatToDo {
        Nothing,
        Replace,
        Exclude
    }

    public static void exitWithUsage() {
        System.out.println("Usage: java " + Find.class.getName() + " TLD fileWithWords outputFile [fix|excl]");
        System.out.println("Where: TLD the extension, e.g. .com");
        System.out.println("       fileWithWords is the file containing domains to lookup");
        System.out.println("       outputFile is the file to output the list of available domains");
        System.out.println("       fix optional, will remove spaces and special characters from the words from your file");
        System.out.println("       excl optional, will remove those words with special characters");
        System.out.println("e.g. "  + Find.class.getName() + " .com mylist.csv available.txt");
        System.exit(-1);
    }

    public static void main(String[] args) {
        if ((args.length != 3) && (args.length != 4)) {
            exitWithUsage();
        }
        String tld = args[0];
        String fileName = args[1];
        String outputFile = args[2];
        WhatToDo fix = WhatToDo.Nothing;
        if (args.length == 4) {
            if (args[3].equals("fix")) {
                fix = WhatToDo.Replace;
            } else if (args[3].equals("excl")) {
                fix = WhatToDo.Exclude;
            } else {
                exitWithUsage();
            }
        }

        System.out.println("Current path: " + Paths.get("").toAbsolutePath().toString());
        System.out.println("tld: " + tld);
        System.out.println("fileName: " + fileName);
        System.out.println("output fileName: " + outputFile);

        File file = new File(outputFile);
        file.delete();

        Find obj = new Find(tld, fileName, outputFile, fix);
        obj.run();
    }
    private String tld;
    private Path fileName;
    private Path outputFile;
    private WhatToDo fix;
    private int totalFound;

    public Find(String tld, String fileName, String outputFile, WhatToDo fix) {
        this.tld = tld;
        this.fileName = Paths.get(fileName);
        this.outputFile = Paths.get(outputFile);
        this.fix = fix;
        this.totalFound = 0;
    }

    private String fixed(String s) {
        return s.replaceAll("[\\ \\/\\'\\!\\\"\\-\\\\\\+\\.\\^:,]","");
    }

    private long amount(Path path) {
        Stream<String> lines = null;
        try {
            lines = Files.lines(path);
        } catch (IOException e) {
            System.out.println("io exception whilst reading " + path.getFileName());
        }
        return lines.count();
    }

    private void writeToFile(String domainName) {
        try {
            totalFound++;
            Files.write(outputFile, (domainName + "\r\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();;
            System.out.println("io exception whilst writing to " + outputFile.getFileName());
        }
    }

    public void run() {
        System.out.println();
        System.out.println("Available domains:");
        long rows = amount(fileName);
        Stream<String> lines = null;
        try {
            ProgressBarBuilder pbb = new ProgressBarBuilder().setInitialMax(rows).showSpeed();

            boolean start = false;
            ProgressBar.wrap(Files.lines(fileName), pbb).forEach(s -> {
                String domainName = s;
                if (fix == WhatToDo.Exclude) {
                    if (domainName.equals(fixed(domainName))) {
                        if (isAvailable(domainName + tld)) {
                            writeToFile(domainName + tld);
                        }
                    }
                } else {
                    if (fix == WhatToDo.Replace) {
                        domainName = fixed(s);
                    }
                    if (isAvailable(domainName + tld)) {
                        writeToFile(domainName + tld);
                    }
                }
            });
        } catch (IOException ex) {
            System.out.println("io exception whilst reading " + fileName.getFileName());
        }
        System.out.println();
        System.out.println("Amount available: " + totalFound);
        System.out.println("Amount verified: " + rows);
        System.out.println("%: " + totalFound / rows * 100);
        System.out.println("Done");

    }

    public boolean isAvailable(String domainName) {
        String whoIsInfo = getWhois(domainName);
        return whoIsInfo.startsWith("No match");
    }

    public String getWhois(String domainName) {

        StringBuilder result = new StringBuilder("");

        WhoisClient whois = new WhoisClient();
        try {
            //default is internic.net
            whois.connect(WhoisClient.DEFAULT_HOST);
            String whoisData1 = whois.query("=" + domainName);
            result.append(whoisData1);
            whois.disconnect();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}
