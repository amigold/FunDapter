package com.ami.fundapter.extractors;


public interface BooleanExtractor<T> {
    public boolean getBooleanValue(T item, int position);
}
