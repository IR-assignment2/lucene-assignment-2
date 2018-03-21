package com.ir;

import com.ir.config.CranConfig;
import com.ir.service.CranProcessor;
import com.ir.service.CranReader;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class CranApp {

    public static void main(String... args) throws ParseException {

        Options options = new Options();
        options.addOption("out", true, "output file path");
        options.addOption("index", true, "index path");

        ClassLoader classLoader = CranApp.class.getClassLoader();
        Properties properties = new Properties();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        try {
            properties.load(new InputStreamReader(classLoader.getResourceAsStream("config.properties")));
            CranConfig config = CranConfig.getCranConfigInstance(cmd.getOptionValue("index"));
//            CranReader.readAndIndex(config);
            CranProcessor.processQueries(properties.getProperty("query"), cmd.getOptionValue("out"), config);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
    }
}
