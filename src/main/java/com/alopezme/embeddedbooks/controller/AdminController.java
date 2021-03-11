package com.alopezme.embeddedbooks.controller;

import com.alopezme.embeddedbooks.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.infinispan.Cache;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private SpringEmbeddedCacheManager cacheManager;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    /***
     * MANAGE CACHES
     */
    @GetMapping("/caches")
    public String getCaches() {

        return cacheManager.getCacheNames().toString();
    }

    /***
     * MANAGE CACHE: SIZE, LOAD, ETC.
     */

    @GetMapping("/cache/size")
    public String getAll()  {
        return  cacheManager.getCache("books").getNativeCache().entrySet().size() + System.lineSeparator();
    }

    @GetMapping("/cache/test")
    public String putOnCache() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Cache< Integer, Book> cache = cacheManager.getNativeCacheManager().getCache("books");
        logger.info("Cache config => " + cache.getCacheConfiguration().toXMLString("books"));

        // Put new entry
        Book book = new Book(1, "Alvaro y la fuerza del sino", "Alvaro", 1993);
        logger.info(mapper.writeValueAsString(book));
        cache.put(1,book);

        // Check new entry
        return mapper.writeValueAsString(cache.get(1));
    }


    @GetMapping("/cache/load")
    public String loadBooksCache() throws IOException {

        Cache< Integer, Book> cache = cacheManager.getNativeCacheManager().getCache("books");
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/books.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Book book = new Book(Integer.valueOf(values[0].trim()), values[1].trim(), values[2].trim(), Integer.valueOf(values[3].trim()));
                logger.info("PUT : " + mapper.writeValueAsString(book));
                cache.put(book.getId(), book);
            }
        }
        return "Books cache now contains " + cache.entrySet().size() + " entries";
    }

    @GetMapping("/cache/miniload")
    public String miniLoadBooksCache() throws IOException {

        Cache< Integer, Book> cache = cacheManager.getNativeCacheManager().getCache("books");
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/books.csv")))) {
            String line;
            int iteration = 0;
            while ((line = br.readLine()) != null && iteration < 100) {
                String[] values = line.split(",");
                Book book = new Book(Integer.valueOf(values[0].trim()), values[1].trim(), values[2].trim(), Integer.valueOf(values[3].trim()));
                logger.info("PUT : " + mapper.writeValueAsString(book));
                cache.put(book.getId(), book);
                iteration++;
            }
        }
        return "Books cache now contains " + cache.entrySet().size() + " entries";
    }


    /***
     * STATISTICS AND CONFIGURATION
     */

    @GetMapping("/cache-manager/configuration")
    public String getCacheManagerConfiguration()  {

        GlobalConfiguration globalConfiguration = cacheManager.getNativeCacheManager().getCacheManagerConfiguration();

        String result = "{" + "\"blockingThreadPoolName\":\"" + globalConfiguration.blockingThreadPoolName() + "\"," +
                "\"blockingThreadPool\":\"" + globalConfiguration.blockingThreadPool().toString() + "\"," +
                "\"cacheManagerName\":\"" + globalConfiguration.cacheManagerName() + "\"," +
                "\"defaultCacheName\":\"" + globalConfiguration.defaultCacheName() + "\"," +
                "\"expirationThreadPoolName\":\"" + globalConfiguration.expirationThreadPoolName() + "\"," +
                "\"expirationThreadPool\":\"" + globalConfiguration.expirationThreadPool().toString() + "\"," +
                "\"globalState\":\"" + globalConfiguration.globalState().attributes().attributes().toString() + "\"" +
//                "\"\":\"" + globalConfiguration. + "\"," +
                "}";// Close JSON
        return result + System.lineSeparator();
    }
}
