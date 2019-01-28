package com.johanw.jdomainbot.util;

import com.johanw.jdomainbot.model.CombinedWords;
import com.johanw.jdomainbot.model.Words;
import com.johanw.jdomainbot.model.WordsFromDirectory;
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

public class DomainHelper {
    public static boolean isAvailable(String domainName) {
        String whoIsInfo = getWhois(domainName);
        return whoIsInfo.startsWith("No match");
    }

    public static String getWhois(String domainName) {
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
