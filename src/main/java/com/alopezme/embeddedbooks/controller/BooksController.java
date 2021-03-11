package com.alopezme.embeddedbooks.controller;

import com.alopezme.embeddedbooks.model.Book;
import com.alopezme.embeddedbooks.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    Logger logger = LoggerFactory.getLogger(BooksController.class);

    @GetMapping("/")
    public String greeting() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        Book book = new Book(1, "Alvaro y la fuerza del sino", "Alvaro", 1993);
        logger.info(mapper.writeValueAsString(book));
        return "ciao ";
    }


}
