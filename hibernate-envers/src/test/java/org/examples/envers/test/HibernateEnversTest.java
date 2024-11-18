package org.examples.envers.test;

import jakarta.persistence.EntityManagerFactory;
import net.bytebuddy.utility.RandomString;
import org.examples.envers.model.AuditRevisionEntity;
import org.examples.envers.model.Person;
import org.examples.envers.repo.PersonRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
public class HibernateEnversTest {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    public void testWhenEntityInserted_ThenHibernateEnversLogged() {
        AuditReader auditReader = getAuditReader();
        Assertions.assertTrue(auditReader.isEntityClassAudited(Person.class));
        Person person = personRepository.save(getPerson());
        List<Number> revisions = auditReader.getRevisions(Person.class, person.getId());
        Person personV1 = auditReader.find(Person.class, person.getId(), revisions.getFirst());
        Assertions.assertEquals(person.getName(), personV1.getName());
    }

    @Test
    public void testWhenEntityDeleted_ThenHibernateEnversLogged() {
        AuditReader auditReader = getAuditReader();
        Person person = personRepository.save(getPerson());
        personRepository.delete(person);
        List<Number> revisions = auditReader.getRevisions(Person.class, person.getId());
        Assertions.assertEquals(2, revisions.size());
        AuditRevisionEntity revision = auditReader.findRevision(AuditRevisionEntity.class, revisions.getLast());
        Assertions.assertNotNull(revision.getUsername());
        Person deletedPerson = auditReader.find(Person.class, person.getId(), revisions.getLast());
        Assertions.assertNull(deletedPerson);
    }

    @Test
    public void testWhenEntityUpdated_ThenHibernateEnversLogged() {
        Person person = personRepository.save(getPerson());
        person.setName("AA");
        personRepository.save(person);
        AuditReader auditReader = getAuditReader();
        List<Number> revisions = auditReader.getRevisions(Person.class, person.getId());
        Assertions.assertEquals(2, revisions.size());
        Person updatedPerson = auditReader.find(Person.class, person.getId(), revisions.getLast());
        Assertions.assertEquals("AA", updatedPerson.getName());
    }

    private Person getPerson() {
        return new Person("name-" + RandomString.make(6), "lasname-" + RandomString.make(6), "t@gmail.com");
    }

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
    }
}
