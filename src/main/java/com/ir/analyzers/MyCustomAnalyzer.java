package com.ir.analyzers;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.en.*;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.wordnet.SynonymTokenFilter;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.apache.lucene.wordnet.SynonymMap;

import java.io.FileInputStream;
import java.io.IOException;

public class MyCustomAnalyzer extends StopwordAnalyzerBase {

    private final CharArraySet stemExclusionSet;

    public static CharArraySet getDefaultStopSet() {
        return EnglishAnalyzer.getDefaultStopSet();
    }

    public MyCustomAnalyzer() {
        this(EnglishAnalyzer.getDefaultStopSet());
    }

    public MyCustomAnalyzer(CharArraySet stopwords) {
        this(stopwords, CharArraySet.EMPTY_SET);
    }

    public MyCustomAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionSet) {
        super(stopwords);
        this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
    }

    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new StandardFilter(source);
        SynonymMap synonymMap;
        try {
            synonymMap = new SynonymMap(new FileInputStream("/home/william/prolog/wn_s.pl"));
            result = new SynonymTokenFilter(result, synonymMap, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = new EnglishPossessiveFilter(result);
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, this.stopwords);
        result = new EnglishMinimalStemFilter(result);
        result = new KStemFilter(result);
        if (!this.stemExclusionSet.isEmpty()) {
            result = new SetKeywordMarkerFilter((TokenStream)result, this.stemExclusionSet);
        }
        result = new SnowballFilter(result, new EnglishStemmer());
        return new TokenStreamComponents(source, result);
    }

    protected TokenStream normalize(String fieldName, TokenStream in) {
        TokenStream result = new StandardFilter(in);
        result = new LowerCaseFilter(result);
        return result;
    }

    private static class DefaultSetHolder {
        static final CharArraySet DEFAULT_STOP_SET;

        private DefaultSetHolder() {
        }
        static {
            DEFAULT_STOP_SET = StandardAnalyzer.STOP_WORDS_SET;
        }
    }
}
