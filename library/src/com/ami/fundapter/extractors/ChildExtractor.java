package com.ami.fundapter.extractors;

public interface ChildExtractor<G,C> {
    public C extractChild(G group, int childPosition);
    
    public int getChildrenCount(G group);
}
