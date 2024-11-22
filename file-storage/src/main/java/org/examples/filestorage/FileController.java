package org.examples.filestorage;

import org.examples.filestorage.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.UUID;

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
    public ResponseEntity<InputStream> getFile(@PathVariable("uid")UUID uid) {
        try {
            InputStream inputStream =fileService.readFileContent(uid);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test" );
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");  // You can customize this based on the file type

            // Return the InputStream in the response body
            return new ResponseEntity<>(inputStream, headers, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
