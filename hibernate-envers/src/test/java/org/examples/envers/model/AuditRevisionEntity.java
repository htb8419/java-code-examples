package org.examples.envers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.examples.envers.test.UserRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(name = "REVISION_INFO",schema = "LOG")
@RevisionEntity(UserRevisionListener.class)
public class AuditRevisionEntity extends DefaultRevisionEntity {

    private String username;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}