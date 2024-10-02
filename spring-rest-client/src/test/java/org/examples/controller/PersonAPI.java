package org.examples.controller;

import org.examples.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonAPI {

    @PostMapping(path = "/save", consumes = "application/json")
    public ResponseEntity<String> save(@RequestBody Person person) {
        return ResponseEntity.ok("Saved " + person);
    }
}
