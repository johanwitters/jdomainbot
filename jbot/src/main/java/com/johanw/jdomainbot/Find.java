package com.johanw.jdomainbot;

import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.net.whois.WhoisClient;

public class Find {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: " + Find.class.getName() + " TLD fileWithWords");
            System.out.println("Where: TLD the extension, e.g. .com");
            System.out.println("       fileWithWords is the file containing domains to lookup");
            System.out.println("e.g. "  + Find.class.getName() + " .com mylist.csv");
            System.exit(-1);
        }
        Find obj = new Find();
        String tld = args[0];
        String fileName = args[1];
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current path: " + s);
        System.out.println("tld: " + tld);
        System.out.println("fileName: " + fileName);
        obj.find(tld, fileName);
    }

    public void find(String tld, String fileName) {
        System.out.println();
        System.out.println("Available domains:");
        Path path = Paths.get(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> {
                if (isAvailable(s + tld)) {
                    System.out.println("  " + s + tld);
                }
            });
        } catch (IOException ex) {
            System.out.println("io exception whilst reading " + fileName);
        }
        System.out.println();
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
