package com.ami.fundapter.extractors;


public interface IntegerExtractor<T> {

    public int getIntValue(T item, int position);
}
