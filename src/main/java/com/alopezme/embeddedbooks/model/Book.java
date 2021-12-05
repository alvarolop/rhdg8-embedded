package com.alopezme.embeddedbooks.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import java.io.Serializable;

@Indexed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id","title","author","publicationYear"})
public class Book implements Serializable {

    @GenericField(searchable = Searchable.NO, projectable = Projectable.NO)
    @JsonProperty("id")
    private int id;

    @FullTextField(searchable = Searchable.YES, projectable = Projectable.YES)
    @JsonProperty("title")
    private String title;

    @KeywordField(searchable = Searchable.YES, projectable = Projectable.YES)
    @JsonProperty("author")
    private String author;

    @GenericField(searchable = Searchable.YES, projectable = Projectable.YES)
    @JsonProperty("publicationYear")
    private int publicationYear;

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}