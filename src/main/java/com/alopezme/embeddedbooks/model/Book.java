package com.alopezme.embeddedbooks.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.hibernate.search.annotations.*;

import java.io.Serializable;

@Indexed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id","title","author","publicationYear"})
public class Book implements Serializable {

    @Field(index=Index.NO, store = Store.NO, analyze = Analyze.NO)
    @JsonProperty("id")
    private int id;

    @Field(index=Index.YES, store = Store.YES, analyze = Analyze.NO)
    @JsonProperty("title")
    private String title;

    @Field(index=Index.YES, store = Store.YES, analyze = Analyze.YES)
    @JsonProperty("author")
    private String author;

    @Field(index=Index.NO, store = Store.NO, analyze = Analyze.NO)
    @DateBridge(resolution= Resolution.YEAR)
    @JsonProperty("publicationYear")
    private int publicationYear;

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}