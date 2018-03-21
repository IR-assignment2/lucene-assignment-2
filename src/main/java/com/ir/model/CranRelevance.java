package com.ir.model;

public class CranRelevance {

    private int cranID;
    private CranQuery cranQuery;
    private int relevance;

    public int getCranID() {
        return cranID;
    }

    public void setCranID(int cranID) {
        this.cranID = cranID;
    }

    public CranQuery getCranQuery() {
        return cranQuery;
    }

    public void setCranQuery(CranQuery cranQuery) {
        this.cranQuery = cranQuery;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }
}
