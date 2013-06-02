package com.ami.fundapter;

public interface ChildExtractor<G,C> {
    public C extractChild(G group, int childPosition);
    
    public int getChildrenCount(G group);
}
