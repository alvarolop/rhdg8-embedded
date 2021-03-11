package com.alopezme.embeddedbooks.configuration;

import com.alopezme.embeddedbooks.model.Book;
import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
        includeClasses = {
                Book.class
        },
        schemaFileName = "book.proto",
        schemaFilePath = "proto/",
        schemaPackageName = "org.alopezme.embeddedbooks")
public interface BookSchema extends SerializationContextInitializer {
}