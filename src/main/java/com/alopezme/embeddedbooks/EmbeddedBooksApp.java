package com.alopezme.embeddedbooks;

import org.infinispan.spring.embedded.session.configuration.EnableInfinispanEmbeddedHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableInfinispanEmbeddedHttpSession
public class EmbeddedBooksApp {

    public static void main(String... args) {
        SpringApplication.run(EmbeddedBooksApp.class, args);
    }
}
