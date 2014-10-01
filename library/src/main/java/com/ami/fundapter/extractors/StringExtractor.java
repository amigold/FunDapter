package com.ami.fundapter.extractors;


/**
 * Interface to extract String data from a specified model object
 * @author Amig
 *
 * @param <T>
 */
public interface StringExtractor<T> {
    public String getStringValue(T item, int position);
}
