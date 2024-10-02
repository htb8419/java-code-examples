package org.examples.service.impl;

import org.examples.model.Person;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PersonServiceImpl {

    @Cacheable(value = "personCache", cacheManager = "cacheManager")
    public String save(Person person) {
        System.out.println("save person object");
        return person.toString();
    }

    public Person findByCode(String personCode) {
        return new Person(
                "N" + randomString(),
                "F" + randomString(),
                personCode, (int) (10 + Math.random() * 50));
    }

    public String randomString() {
        return "" + (char) (new Random().nextInt('z' - 'a') + 'a');
    }
}
