package com.ir.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.query.QueryAutoStopWordAnalyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class CranConfig {

    private static CranConfig instance = null;

    private Analyzer analyzer;
    private Directory index;
    private IndexWriterConfig config;
    private Similarity similarity;

    private CranConfig(String indexPath) throws IOException {
        // Try other analyzers as well Standard, Stopword, etc - in report
//        analyzer = new WhitespaceAnalyzer();
//        analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        analyzer = new EnglishAnalyzer();
         index = new SimpleFSDirectory(Paths.get(indexPath));
//        index = new SimpleFSDirectory(Paths.get());
//        index = new RAMDirectory();
        config = new IndexWriterConfig(analyzer);
//        similarity = new ClassicSimilarity();
        similarity = new MultiSimilarity(new Similarity[] {new BM25Similarity(), new ClassicSimilarity()});
//        similarity = new BM25Similarity();
        config.setSimilarity(similarity);
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public Directory getIndex() {
        return index;
    }

    public IndexWriterConfig getConfig() {
        return config;
    }

    public Similarity getSimilarity() {
        return similarity;
    }

    public static CranConfig getCranConfigInstance(String indexPath) throws IOException {
        if (instance == null) {
            instance = new CranConfig(indexPath);
            return instance;
        } else {
            return instance;
        }
    }


}