package org.examples.jdbc;

import org.examples.jdbc.model.FileStorage;
import org.examples.jdbc.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
public class FluxController {
    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/flux/{fileId}")
    public ResponseEntity<Mono<InputStreamReader>> getFile(@PathVariable Integer fileId) throws Exception {
        final FileStorage fileStorage = fileStorageService.get(fileId);
        String fileName = fileStorage.getFileName();
        String contentDisposition = ContentDisposition.builder("attachment")
                .filename(fileName)
                .build().toString();
        try(final InputStream inputStream = fileStorage.getFileContent().getBinaryStream()) {
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType(fileStorage.getFileContentType()))
                    .contentLength(fileStorage.getFileContent().length())
                    .header(CONTENT_DISPOSITION, contentDisposition)
                    .body(Mono.just(inputStreamReader));
        }
    }
}
