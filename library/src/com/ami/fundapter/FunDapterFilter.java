package com.ami.fundapter;

import java.util.ArrayList;

public interface FunDapterFilter<T> {
    public ArrayList<T> filter(String filterConstraint,
	    ArrayList<T> originalList);
}
