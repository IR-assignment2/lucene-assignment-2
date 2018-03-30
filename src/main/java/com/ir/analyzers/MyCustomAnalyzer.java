package com.ir.analyzers;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.tartarus.snowball.ext.EnglishStemmer;

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
        result = new EnglishPossessiveFilter(result);
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, this.stopwords);
//        result = new SnowballFilter(result)
        if (!this.stemExclusionSet.isEmpty()) {
            result = new SetKeywordMarkerFilter((TokenStream)result, this.stemExclusionSet);
        }

        result = new SnowballFilter(result, new EnglishStemmer());
//        result = new PorterStemFilter((TokenStream)result);
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
