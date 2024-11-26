package org.examples.auditing.test;

import org.examples.auditing.model.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;

public class UserRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        if (revisionEntity instanceof AuditRevisionEntity auditRevisionEntity) {
            auditRevisionEntity.setUsername("testUser");// get current user from security context
        }
    }
}