package org.examples.jdbc;

import org.examples.jdbc.model.FileStorage;
import org.examples.jdbc.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/file")
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> streamingStoreFile(@RequestParam("file") MultipartFile file) throws Exception {
        try (final InputStream fileContent = file.getInputStream()) {
            fileStorageService.store(file.getName(), file.getContentType(), fileContent);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/nonStreaming")
    public ResponseEntity<?> storeFile(@RequestParam("file") MultipartFile file) throws Exception {
        try (final InputStream fileContent = new ByteArrayInputStream(file.getBytes())) {
            fileStorageService.store(file.getName(), file.getContentType(), fileContent);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<StreamingResponseBody> getFile(@PathVariable Integer fileId) throws Exception {
        final FileStorage fileStorage = fileStorageService.get(fileId);
        String fileName = fileStorage.getFileName();
        String contentDisposition = ContentDisposition.builder("attachment")
                .filename(fileName)
                .build().toString();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(fileStorage.getFileContentType()))
                .contentLength(fileStorage.getFileContent().length())
                .header(CONTENT_DISPOSITION, contentDisposition)
                .body(outputStream -> {
                    try (InputStream fileContent = fileStorage.getFileContent().getBinaryStream()) {
                        StreamUtils.copy(fileContent, outputStream);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
