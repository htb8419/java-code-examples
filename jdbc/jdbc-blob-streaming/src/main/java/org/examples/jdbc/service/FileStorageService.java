package org.examples.jdbc.service;

import org.examples.jdbc.model.FileStorage;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorageService {

    FileStorage store(String fileName, String fileContentType, InputStream fileContent) throws IOException;

    FileStorage get(Integer fileId) throws IOException;
}
