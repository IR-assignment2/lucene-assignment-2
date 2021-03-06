package com.ir.model;

public class FinancialTimesModel {
    private String docNum;
    private String profile;
    private String date;
    private String headline;
    private String text;
    private String publication;
    private String page;

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "FinancialTimesModel{" +
                "docNum='" + docNum + '\'' +
                ", profile='" + profile + '\'' +
                ", date='" + date + '\'' +
                ", headline='" + headline + '\'' +
                ", text='" + text + '\'' +
                ", publication='" + publication + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
