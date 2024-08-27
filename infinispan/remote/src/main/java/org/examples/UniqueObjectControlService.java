package org.examples;

import org.infinispan.Cache;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
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
        System.out.println("objectCount >> " + uniqueObjectCache.size());
        ObjectInfo objectInfo = new ObjectInfo(obj);
        return uniqueObjectCache.putIfAbsent(objectInfo.getKey(), objectInfo, timeStep.getSeconds(), TimeUnit.SECONDS)
                == null;
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
        uniqueObjectCache.entrySet().stream().forEach((key, entry) -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    public static class ObjectInfo {
        final String key;
        final String className;
        final int hashCode;

        public ObjectInfo(Object object) {
            this.className = object.getClass().getSimpleName();
            this.hashCode = object.hashCode();
            this.key = className + hashCode;
        }

        public String getKey() {
            return key;
        }

        public String getClassName() {
            return className;
        }

        public int getHashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return "ObjectInfo{" +
                    "key='" + key + '\'' +
                    ", className='" + className + '\'' +
                    ", hashCode=" + hashCode +
                    '}';
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            ObjectInfo that = (ObjectInfo) object;
            return hashCode == that.hashCode && Objects.equals(key, that.key) && Objects.equals(className, that.className);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, className, hashCode);
        }
    }
}
