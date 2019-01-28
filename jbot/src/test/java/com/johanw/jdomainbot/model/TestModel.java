package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.model.selector.MaxLength;
import com.johanw.jdomainbot.model.selector.MinLength;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestModel {
    @Test
    public void test1() throws IOException {
        String[] words1 = new String[] {"Value1", "Value2"};
        String[] words2 = new String[] {"SecondValue1", "SecondValue2"};
        String[] words3 = new String[] {"A"};
        String[] words4 = new String[] {"bbb", "b", "c"};

        List<Words> allWords = new ArrayList<>();
        allWords.add(new WordsFromArray(words1));
        allWords.add(new WordsFromArray(words2));
        allWords.add(new WordsFromArray(words3));
        allWords.add(new WordsFromArray(MaxLength.from(1), words4));

        Words words = CombinedWords.from(allWords);
        Assert.assertEquals(words.size(), 8);
        Assert.assertEquals(words.next(), "Value1SecondValue1Ab");
        Assert.assertEquals(words.next(), "Value1SecondValue1Ac");
        Assert.assertEquals(words.next(), "Value1SecondValue2Ab");
        Assert.assertEquals(words.next(), "Value1SecondValue2Ac");
        Assert.assertEquals(words.next(), "Value2SecondValue1Ab");
        Assert.assertEquals(words.next(), "Value2SecondValue1Ac");
        Assert.assertEquals(words.next(), "Value2SecondValue2Ab");
        Assert.assertEquals(words.next(), "Value2SecondValue2Ac");
    }

    @Test
    public void test2() throws IOException {
        String[] words1 = new String[] {"Value1", "Value2", "Val", "Aabbcc"};

        Words words = new WordsFromArray(MinLength.from(4), words1);
        Assert.assertEquals(words.size(), 3);
        Assert.assertEquals(words.next(), "Value1");
        Assert.assertEquals(words.next(), "Value2");
        Assert.assertEquals(words.next(), "Aabbcc");
    }

}
