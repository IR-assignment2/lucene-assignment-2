package com.ir.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.config.CranConfig;
import com.ir.model.*;
import org.apache.lucene.index.IndexWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CranReader {

    public static void readAndIndex(CranConfig config) throws IOException {

        ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();

        ObjectMapper objectMapper = new ObjectMapper();
        String[] jsonFiles = new String[] {
                classLoader.getResource("fbis_parsed.json").getPath(),
                classLoader.getResource("fr94_parsed.json").getPath(),
                classLoader.getResource("ft_parsed.json").getPath(),
                classLoader.getResource("latimes_parsed.json").getPath(),
        };
//
//        String[] jsonFiles = new String[] {
//                "/Users/apple/projects/CS7IS3-information-retrieval/assginment2/luceneassignment2/src/main/resources/fbis_parsed.json",
//                "/Users/apple/projects/CS7IS3-information-retrieval/assginment2/luceneassignment2/src/main/resources/fr94_parsed.json",
//                "/Users/apple/projects/CS7IS3-information-retrieval/assginment2/luceneassignment2/src/main/resources/ft_parsed.json",
//                "/Users/apple/projects/CS7IS3-information-retrieval/assginment2/luceneassignment2/src/main/resources/latimes_parsed.json"
//        };

        IndexWriter writer = Indexer.getWriter(config);

        for (int i=0; i<jsonFiles.length; i++) {
            String doc = new String(Files.readAllBytes(Paths.get(jsonFiles[i])));
            List<NewsModel> data = objectMapper.readValue(doc, new TypeReference<List<NewsModel>>(){});

            for (NewsModel newsModel : data) {
                Indexer.addDocToIndex(newsModel, writer);
            }
        }

        writer.close();
    }


    public static List<CranQuery> readQueries(String file, CranConfig config) throws IOException {
        String line;
        Integer id = 0;

        List<CranQuery> cranQueries = new ArrayList<>();

        ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();
        try (BufferedReader br = new BufferedReader(
                (new InputStreamReader(classLoader.getResourceAsStream(file))))) {

            CranQuery cqr = null;
            StringBuilder stringBuilder = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(".I")) {
                    id += 1;
                    if (cqr != null && stringBuilder != null) {
                        cqr.setQuery(stringBuilder.toString());
                        cranQueries.add(cqr);
                    }
                    cqr = new CranQuery();
                    int queryId = Integer.parseInt(line.replace(".I", "").trim());
                    cqr.setId(id);
                    cqr.setQueryId(queryId);
                } else if (line.startsWith(".W")) {
                    stringBuilder = new StringBuilder();
                } else {
                    if (stringBuilder != null) stringBuilder.append(line);
                }
            }
            if (cqr != null && stringBuilder != null) {
                cqr.setQuery(stringBuilder.toString());
                cranQueries.add(cqr);
            }
        }
        return cranQueries;
    }

}
