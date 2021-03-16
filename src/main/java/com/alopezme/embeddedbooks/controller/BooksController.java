package com.alopezme.embeddedbooks.controller;

import com.alopezme.embeddedbooks.model.Book;
import com.alopezme.embeddedbooks.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books")
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    Logger logger = LoggerFactory.getLogger(BooksController.class);

    /**
     * GET ALL
     */
    @GetMapping("/keys")
    public String getKeys() {
        return bookRepository.getKeys();
    }

    @GetMapping("/entries")
    public String getEntries() {
        return bookRepository.getValues();
    }






    /***
     * QUERIES: GET
     */

    @GetMapping("/query/title")
    public String queryByTitle()  {
        return bookRepository.query("FROM com.alopezme.embeddedbooks.model.Book WHERE title='The Iliad'").toString();
    }

    @GetMapping("/query/author")
    public String queryByAuthor()  {
        return bookRepository.query("FROM com.alopezme.embeddedbooks.model.Book WHERE author:'Homer'").toString();
    }







    /***
     * QUERIES: REMOVE
     */

    @GetMapping("/query/remove-01")
    public String queryRemove01()  {

        List<Book> list = bookRepository.query("FROM com.alopezme.embeddedbooks.model.Book WHERE title='The Iliad'");
        logger.info("Removing ... " + list.toString());
        for (Book book : list ) {
            bookRepository.delete(book.getId());
        }
        return list.toString();
    }

    @GetMapping("/query/remove-02")
    public String queryRemove02()  {

        List<Object[]> list = bookRepository.queryObject("SELECT id FROM com.alopezme.embeddedbooks.model.Book WHERE author:'Homer'");
        List<Integer> result = new ArrayList<Integer>();
        for (Object[] book : list ) {
            logger.info("Removing book " + Integer.toString((Integer)book[0]));
            bookRepository.delete((Integer)book[0]);
            result.add((Integer)book[0]);
        }
        return result.toString();
    }

    @GetMapping("/query/remove-03")
    public String queryRemove03()  {
        List<Object[]> list = bookRepository.queryObject("SELECT id FROM com.alopezme.embeddedbooks.model.Book WHERE author:'Homer'");
        Set<Integer> listToRemove = list.stream()
                .map(row -> (Integer) row[0])
                .collect(Collectors.toSet());

        if (listToRemove.isEmpty())
            return "The remove operation does not contain any entry.";

        boolean status = bookRepository.bulkRemove(listToRemove);
        if (status)
            return listToRemove.toString();
        else
            return "Remove operation failed." + System.lineSeparator();
    }
}

