package org.examples.filestorage;

import org.examples.filestorage.model.dto.FileDto;
import org.examples.filestorage.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping()
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUid = fileService.storeFile(file);
            return ResponseEntity.ok(fileUid);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{uid}")
    public ResponseEntity<StreamingResponseBody> getFile(@PathVariable("uid") UUID uid) {
        try {
            FileDto fileDto = fileService.readFile(uid);
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, fileDto.contentType())
                    .header(CONTENT_LENGTH, String.valueOf(fileDto.size()))
                    .body(outputStream -> StreamUtils.copy(fileDto.content(), outputStream));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
