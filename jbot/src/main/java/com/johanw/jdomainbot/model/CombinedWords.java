package com.johanw.jdomainbot.model;

import com.johanw.jdomainbot.util.SplitHelper;

import java.util.List;

public class CombinedWords implements Words {
    private static final SplitHelper<Words> helper = new SplitHelper<Words>();
    private Words words1;
    private Words words2;
    private String words1Word;

    public CombinedWords(Words words1, Words words2) {
        this.words1 = words1;
        this.words2 = words2;
        if (words1.hasNext())
            words1Word = words1.next();
        else
            throw new RuntimeException("No point running this without words in words1");
        if (!words2.hasNext())
            throw new RuntimeException("No point running this without words in words2");
    }

    @Override
    public long size() {
        return words1.size() * words2.size();
    }

    @Override
    public boolean hasNext() {
        return words1.hasNext() || words2.hasNext();
    }

    @Override
    public String next() {
        if (!words2.hasNext()) {
            if (words1.hasNext()) {
                words1Word = words1.next();
                words2.reset();
            }
        }
        if (words2.hasNext()) {
            return words1Word + words2.next();
        } else {
            return words1Word + words2.next();
        }
    }

    public void reset() {
        words1.reset();
        words2.reset();
    }

    public static Words from(List<Words> otherWords) {
        SplitHelper.Split<Words> split = helper.split(otherWords);
        return from(split.getFirst(), split.getRest());
    }

    public static Words from(Words words1, List<Words> otherWords) {
        if (otherWords.size() == 0) return words1;
        if (otherWords.size() == 1) return new CombinedWords(words1, otherWords.get(0));
        SplitHelper.Split<Words> split = helper.split(otherWords);
        return from(new CombinedWords(words1, split.getFirst()), split.getRest());
    }
}
