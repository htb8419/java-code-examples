package org.examples;

import org.infinispan.Cache;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class UniqueObjectControlService {
    private final Cache<String, Object> uniqueObjectCache;

    public UniqueObjectControlService(Cache<String, Object> uniqueObjectCache) {
        this.uniqueObjectCache = uniqueObjectCache;
    }

    public boolean addObject(Object obj) {
        return addObject(obj, null);
    }

    public boolean addObject(Object obj, Duration timeStep) {
        if (timeStep == null) {
            timeStep = Duration.ofSeconds(60);
        }
        log("cache.size=%d \n", uniqueObjectCache.size());
        ObjectInfo objectInfo = new ObjectInfo(obj);
        return uniqueObjectCache
                .putIfAbsent(objectInfo.getKey(), objectInfo, timeStep.getSeconds(), TimeUnit.SECONDS) == null;
    }

    public void evict(Object obj) {
        ObjectInfo objectInfo = new ObjectInfo(obj);
        uniqueObjectCache.evict(objectInfo.getKey());
    }

    public boolean contains(Object obj) {
        ObjectInfo objectInfo = new ObjectInfo(obj);
        return uniqueObjectCache.containsKey(objectInfo.getKey());
    }

    public void printAll() {
        log("print cache entries \n");
        log("cache.size=%d \n", uniqueObjectCache.size());
        uniqueObjectCache.entrySet().stream().forEach((key, entry) -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    private void log(String message, Object... args) {
        System.out.printf(message, args);
    }
}
