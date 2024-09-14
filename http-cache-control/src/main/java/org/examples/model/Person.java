package org.examples.model;

import java.util.Objects;

public record Person(String firstname,
                     String lastname,
                     String uniqueCode,
                     int age) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(lastname, person.lastname) && Objects.equals(firstname, person.firstname) && Objects.equals(uniqueCode, person.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, uniqueCode, age);
    }
}