package org.examples.filestorage.service;

import org.examples.filestorage.model.FileContent;
import org.examples.filestorage.model.FileInfo;
import org.examples.filestorage.model.dto.FileDto;
import org.examples.filestorage.repo.FileContentRepository;
import org.examples.filestorage.repo.FileRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;
    private final FileContentRepository fileContentRepository;
    private final SessionFactory sessionFactory;

    public FileService(FileRepository fileRepository, FileContentRepository fileContentRepository, SessionFactory sessionFactory) {
        this.fileRepository = fileRepository;
        this.fileContentRepository = fileContentRepository;
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public String storeFile(MultipartFile file) throws IOException {
        //Blob blob = streamToBlob(file.getInputStream(), file.getSize());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(file.getOriginalFilename());
        fileInfo.setFileSize(file.getSize());
        fileInfo = fileRepository.save(fileInfo);
        FileContent fileContent = new FileContent(fileInfo.getId(), fileInfo.getUid(), file.getBytes());
        fileContentRepository.save(fileContent);
        return fileInfo.getUid().toString();
    }

    public FileDto readFileContent(UUID uid) {
        byte[] content = fileContentRepository.findByUid(uid).orElseThrow().getContent();
        return new ByteArrayInputStream(content);
    }

    public Blob streamToBlob(InputStream content, long size) {
        return sessionFactory.openSession().getLobHelper().createBlob(content, size);
    }
}
