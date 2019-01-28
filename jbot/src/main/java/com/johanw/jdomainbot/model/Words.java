package com.johanw.jdomainbot.model;

import java.util.Iterator;
import java.util.Spliterator;

public interface Words extends Iterator<String> {
    long size();
    void reset();
}
