package org.examples.mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public record Employee(String id, String firstname, String lastname, int age, Address address) {

}
