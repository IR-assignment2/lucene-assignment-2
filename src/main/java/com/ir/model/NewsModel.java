package com.ir.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewsModel {
    @JsonProperty("doc_no")
    private String docNo;

    private String title;

    private String content;

    private String date;

    private String summary;

    public NewsModel() {
        super();
    }

    public NewsModel(String docNo, String title, String content, String date, String summary) {
        this.docNo = docNo;
        this.title = title;
        this.content = content;
        this.date = date;
        this.summary = summary;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) { this.summary = summary; }

    @Override
    public String toString() {
        return "NewsModel{" +
                "docNo='" + docNo + '\'' +
                '}';
    }
}
