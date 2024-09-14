package org.examples.service.impl;

import org.examples.model.Person;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl {

    @Cacheable(value = "personCache", cacheManager = "cacheManager")
    public String save(Person person) {
        System.out.println("save person object");
        return person.toString();
    }
}
