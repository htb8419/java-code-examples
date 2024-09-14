package org.examples.endpoint;

import org.examples.model.Person;
import org.examples.service.impl.PersonServiceImpl;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/person")
public class PersonController {
    final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Person person) {
        return ResponseEntity
                .ok().cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(personService.save(person));
    }

    @GetMapping("/findWithCode/{personCode}")
    public ResponseEntity<?> find(@PathVariable String personCode) {
        return ResponseEntity
                .ok().cacheControl(CacheControl.maxAge(Duration.ofSeconds(30)))
                .body(new Person("ali", "najafi", personCode, 30));
    }
}
