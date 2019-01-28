package com.johanw.jdomainbot.util;

import java.util.List;

public class SplitHelper<T> {
    public static class Split<T> {
        T first;
        List<T> rest;

        public Split(T first, List<T> rest) {
            this.first = first;
            this.rest = rest;
        }

        public T getFirst() {
            return first;
        }

        public List<T> getRest() {
            return rest;
        }
    }

    public Split<T> split(List<T> toSplit) {
        T first = toSplit.get(0);
        toSplit.remove(first);
        return new Split(first, toSplit);
    }
}
