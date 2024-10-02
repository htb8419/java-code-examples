package org.examples.endpoint;

import org.examples.service.impl.PersonServiceImpl;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/person")
public class PersonController {
    final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping("/findWithCode/{personCode}")
    public ResponseEntity<?> find(@PathVariable String personCode) {
        return ResponseEntity
                .ok().cacheControl(CacheControl.maxAge(Duration.ofSeconds(30)))
                .body(personService.findByCode(personCode));
    }
}
