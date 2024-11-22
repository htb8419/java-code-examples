package org.examples.jdbc.service;

import org.examples.jdbc.model.FileStorage;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final SessionFactory sessionFactory;

    public FileStorageServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public FileStorage store(String fileName, String fileContentType, InputStream fileContent) throws IOException {
        Session session=getSession();
        final Blob fileContentAsBlob = LobHelper.createBlob(session, fileContent, fileContent.available());
        FileStorage fileStorage = new FileStorage(fileName, fileContentType, fileContentAsBlob);
        session.save(fileStorage);
        return fileStorage;
    }

    @Transactional
    public FileStorage get(Integer fileId) throws IOException {
        final Session session = getSession();
        final FileStorage fileStorage = session.get(FileStorage.class, fileId);
        //Hibernate.initialize(fileStorage);
        return fileStorage;
    }
    Session getSession(){
        final Session session = sessionFactory.getCurrentSession();
        Assert.isTrue(session.isOpen(), "can not open session");
        return session;
    }
}
