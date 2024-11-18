package org.examples.envers.test;

import org.examples.envers.model.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;

public class UserRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        if (revisionEntity instanceof AuditRevisionEntity auditRevisionEntity) {
            auditRevisionEntity.setUsername("testUser");// get current user from security context
        }
    }
}