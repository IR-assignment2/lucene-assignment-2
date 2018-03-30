package com.ir.service;

import com.ir.analyzers.MyTextField;
import com.ir.config.CranConfig;
import com.ir.model.Cran;
import com.ir.model.NewsModel;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;

public class Indexer {


    public static IndexWriter getWriter(CranConfig config) throws IOException {
        IndexWriter writer = new IndexWriter(config.getIndex(), config.getConfig());
        return writer;
    }

//    public static void addCranToIndex(Cran cran, IndexWriter writer) throws IOException {
//        if (!writer.isOpen()) throw new IOException("writer closed");
//        Document document = new Document();
//        document.add(new StoredField("id", cran.getId()));
//        document.add(new TextField("title", cran.getTitle(), Field.Store.YES));
//        document.add(new TextField("author", cran.getAuthor(), Field.Store.YES));
//        document.add(new TextField("source", cran.getSource(), Field.Store.YES));
//        document.add(new TextField("text", cran.getText(), Field.Store.YES));
//        writer.addDocument(document);
//    }

    public static void addDocToIndex(NewsModel newsModel, IndexWriter writer) throws IOException {
        if (!writer.isOpen()) throw new IOException("writer closed");
        Document document = new Document();
        document.add(new StoredField("id", newsModel.getDocNo()));
        document.add(new MyTextField("title", newsModel.getTitle() != null ? newsModel.getTitle() : "null value for title"));
        document.add(new MyTextField("content", newsModel.getContent() != null ? newsModel.getContent() : "null value for content"));
        writer.addDocument(document);
        System.out.println(newsModel.getDocNo() + " indexed.");
    }

}
