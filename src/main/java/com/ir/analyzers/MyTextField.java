package com.ir.analyzers;


import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

public class MyTextField extends Field {
    public static final FieldType TYPE_STORED = new FieldType();

    public MyTextField(String name, String value) {
        super(name, value, TYPE_STORED);
    }

    static {
        TYPE_STORED.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        TYPE_STORED.setTokenized(true);
        TYPE_STORED.setStored(true);
        TYPE_STORED.freeze();
    }
}
