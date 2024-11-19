package org.examples.validation;

import org.examples.validation.model.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @PostMapping
    public ResponseEntity<String> save(@Validated @RequestBody PersonDto personDto) {
       /*, BindingResult result
        System.out.println(result.getErrorCount());
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        }*/
        System.out.println(personDto);
        return ResponseEntity.ok().body("OK");
    }
}
