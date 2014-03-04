package com.ami.fundapter.extractors;


public interface LongExtractor<T> {

    public long getLongValue(T item, int position);
}
