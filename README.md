# jdomainbot

## Summary

This tool allows you to bulk search a list of domain names and check for their availability. The list of domains is provided in a csv file you provide.
You can find all sorts of word dictionaries by googling. I've tested this with a list of 450,000 words. This took about 4 hours to find the available domain names.
The tool displays a command line progress bar, with estimated time left.
The output is stored in a file, which you can open whilst the tool is running.
Happy domain name hunting.

## How

```
Usage: java com.johanw.jdomainbot.Find TLD fileWithWords outputFile [fix]
Where: TLD the extension, e.g. .com
       fileWithWords is the file containing domains to lookup
       outputFile is the file to output the list of available domains
       fix keyword can be added which will remove spaces and special characters from the words from the fileWithWords. For example, john's stuff will become johnsstuff when you specify the fix parameter
```

## Build
```
mvn clean install
```

## Command line example

#### Example contents of mylist.csv:

```
johan
peter
john
thissurelydoesntexistdoesit
johny
doesthisexist
iwouldntknow
```

#### Output of running java com.johanw.jdomainbot.Find .com jbot/src/main/resources/words.txt available.txt:

```
/usr/lib/jvm/java-1.11.0-openjdk-amd64/bin/java -javaagent:/snap/intellij-idea-community/113/lib/idea_rt.jar=41587:/snap/intellij-idea-community/113/bin -Dfile.encoding=UTF-8 -classpath /home/johan/Source/jdomainbot/jbot/target/classes:/home/johan/.m2/repository/commons-net/commons-net/3.3/commons-net-3.3.jar:/home/johan/.m2/repository/me/tongfei/progressbar/0.7.2/progressbar-0.7.2.jar:/home/johan/.m2/repository/org/jline/jline/3.7.0/jline-3.7.0.jar:/home/johan/.m2/repository/org/apache/logging/log4j/log4j-slf4j-impl/2.10.0/log4j-slf4j-impl-2.10.0.jar:/home/johan/.m2/repository/org/slf4j/slf4j-api/1.8.0-alpha2/slf4j-api-1.8.0-alpha2.jar:/home/johan/.m2/repository/org/apache/logging/log4j/log4j-api/2.10.0/log4j-api-2.10.0.jar:/home/johan/.m2/repository/org/apache/logging/log4j/log4j-core/2.10.0/log4j-core-2.10.0.jar com.johanw.jdomainbot.Find .com jbot/src/main/resources/words.txt available.txt fix
Current path: /home/johan/Source/jdomainbot
tld: .com
fileName: jbot/src/main/resources/words.txt
output fileName: available.txt

Available domains:
   1% │▍                   │   9096/466510 (0:04:32 / 3:48:14) 33.4/s
```

## Intellij screenshots
![Run Configuration](img/runconfiguration.png)

![Environment](img/intellij.png)
