package com.alopezme.embeddedbooks.controller;

import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.session.MapSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("session")
public class SessionsController {

    public static final String LATEST_SESSION_VALUE = "latest";
    @Autowired
    private SpringEmbeddedCacheManager cacheManager;

    @GetMapping("/")
    public String sessions() {
        return cacheManager.getCache("sessions").getNativeCache().keySet().toString();
    }

    @GetMapping("/{id}")
    public String sessionContent(@PathVariable String id) {
        SimpleValueWrapper simpleValueWrapper = (SimpleValueWrapper) cacheManager.getCache("sessions").get(id);
        if (simpleValueWrapper == null) {
            return "Session not found";
        }
        MapSession mapSession = (MapSession) simpleValueWrapper.get();
        return "Latest " + mapSession.getAttribute(LATEST_SESSION_VALUE);
    }
}
