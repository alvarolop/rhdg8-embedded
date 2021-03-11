package com.alopezme.embeddedbooks.repository;

import com.alopezme.embeddedbooks.model.Book;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@CacheConfig(cacheNames="books")
@Repository
public class BookRepository {

    @Cacheable(key="#id")
    public Book findById(String id){
        return null;
    }

    @CachePut(key="#id")
    public Book insert(String id, Book c){
        return c;
    }

    @CacheEvict(key="#id")
    public void delete(String id){
    }
}