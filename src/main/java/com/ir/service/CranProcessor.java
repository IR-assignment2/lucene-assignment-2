package com.ir.service;

import com.ir.config.CranConfig;
import com.ir.model.CranQuery;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.lucene.util.IOUtils.UTF_8;


public class CranProcessor {

    private static final int HITS_PER_PAGE = 1000;

    public static void processQueries(String readFile, String outputFile, CranConfig config) throws IOException, ParseException {
        List<CranQuery> queryList = CranReader.readQueries(readFile, config);

        IndexReader reader = DirectoryReader.open(config.getIndex());
        IndexSearcher searcher = new IndexSearcher(reader);
        List<String> fields = reader.document(1).getFields()
                .stream()
                .map(IndexableField::name)
                .collect(Collectors.toList());

        Map<String, Float> boosts = new HashMap<>();
        boosts.put("title", 0.1f);
        boosts.put("content", 18f);
        boosts.put("summary", 0.8f);
        boosts.put("date", 0.008f);

        searcher.setSimilarity(config.getSimilarity());

        Path path = Paths.get(outputFile);

        Files.write(path, "".getBytes());

        for (CranQuery cranQuery: queryList) {
            System.out.println("Query: " + cranQuery.getQuery());
            MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] {"title", "content", "date", "summary"}, config.getAnalyzer(), boosts);
            Query query = parser.parse(cranQuery.getQuery());
            TopDocs results = searcher.search(query, HITS_PER_PAGE);
            ScoreDoc[] hits = results.scoreDocs;
            for (int i=0; i< hits.length; i++) {
                Document doc = searcher.doc(hits[i].doc);
                String num = doc.get("id");
                String out = String.format(cranQuery.getQueryId() + " Q0 %s %s " + hits[i].score + " default", num, i+1);
                Files.write(path, (out + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }

        System.out.println("output written to " + path);
    }

}
