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

        Option c = Option.builder("parsedfiles")
                .hasArgs() // sets that number of arguments is unlimited
                .build();

        Options options = new Options();
        options.addOption("out", true, "output file path");
        options.addOption("index", true, "index path");
        options.addOption("runindexing", true, "index path");
        options.addOption(c);

        ClassLoader classLoader = CranApp.class.getClassLoader();
        Properties properties = new Properties();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String[] inputFiles = cmd.getOptionValues("parsedfiles");

        try {
            properties.load(new InputStreamReader(classLoader.getResourceAsStream("config.properties")));
            CranConfig config = CranConfig.getCranConfigInstance(cmd.getOptionValue("index"));
            if (cmd.getOptionValue("runindexing") != null && cmd.getOptionValue("runindexing").equals("true")) {
                CranReader.readAndIndex(config, inputFiles);
            }
            CranProcessor.processQueries(properties.getProperty("query"), cmd.getOptionValue("out"), config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
    }
}
