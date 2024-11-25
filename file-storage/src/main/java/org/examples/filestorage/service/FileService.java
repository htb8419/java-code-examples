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
    public String storeFile(MultipartFile multipartFile) throws IOException {
        FileInfo fileInfo = getInstanceWithMultipartFile(multipartFile);
        fileInfo = fileRepository.save(fileInfo);
        FileContent fileContent = new FileContent(fileInfo.getId(), fileInfo.getUid(), multipartFile.getBytes());
        fileContentRepository.save(fileContent);
        return fileInfo.getUid().toString();
    }

    public FileDto readFile(UUID uid) {
        FileInfo fileInfo = fileRepository.findByUid(uid).orElseThrow();
        byte[] content = fileContentRepository.findByUid(uid).orElseThrow().getContent();
        return new FileDto(fileInfo.getFilename(), fileInfo.getContentType(), fileInfo.getFileSize().intValue(), content);
    }

    public Blob streamToBlob(InputStream content, long size) {
        return sessionFactory.openSession().getLobHelper().createBlob(content, size);
    }

    public FileInfo getInstanceWithMultipartFile(MultipartFile multipartFile) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(multipartFile.getOriginalFilename());
        fileInfo.setContentType(multipartFile.getContentType());
        fileInfo.setFileSize(multipartFile.getSize());
        return fileInfo;
    }
}
