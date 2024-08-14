package org.examples.jdbc.service;

import org.hibernate.Session;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Clob;


public class LobHelper {

    public static Blob createBlob(Session session, InputStream content, long size) {
        return session.getLobHelper().createBlob(content, size);
    }

    public Clob createClob(Session session, InputStream content, long size, Charset charset) {
        return session.getLobHelper().createClob(new InputStreamReader(content, charset), size);
    }
}